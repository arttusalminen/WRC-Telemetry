package wrc.telemetry.stages;

import generic.data.recording.DataRecorder;
import wrc.telemetry.data.UIDs.StageResultStatus;
import wrc.telemetry.data.types.*;

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;

public class StageContainer {

    private static final String FILE_NAME = "BestRuns.txt";

    private final DataRecorder<WrcData> dataRecorder;
    private final HashMap<StageRecordKey, StageRecording> bestRuns;
    private long lastUpdated = System.currentTimeMillis();


    public StageContainer(DataRecorder<WrcData> dataRecorder) {
        this.dataRecorder = dataRecorder;
        this.bestRuns = retrieveOldStages();
        // Create a new timer that will run the stage container every 0.5 seconds
        Timer t = new Timer("StageContainer");
        TimerTask task = new TimerTask() {
            public void run() {
                StageContainer.this.tick();
            }
        };
        t.schedule(task, 1, 1);
    }

    private HashMap<StageRecordKey, StageRecording> retrieveOldStages() {
        try {
            return serializeDataIn();
        }
        catch (IOException | ClassNotFoundException e) {
            try {
                serializeDataOut(new HashMap<StageRecordKey, StageRecording>());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return new HashMap<StageRecordKey, StageRecording>();
        }
    }

    public static void serializeDataOut(HashMap<StageRecordKey, StageRecording> ish) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static HashMap<StageRecordKey, StageRecording> serializeDataIn() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fin);
        HashMap<StageRecordKey, StageRecording> stageMap = (HashMap<StageRecordKey, StageRecording>) ois.readObject();
        ois.close();
        return stageMap;
    }

    void tick() {
        if (hasStageEnded()) {
            handleStageEnding();
        } else if (hasStageStarted()) {
            handleStageStarting();
        }
    }

    private void handleStageEnding() {
        List<WrcCustom1Data> thisRun = dataRecorder.getAllData(WrcCustom1Data.class);
        if (thisRun.isEmpty()) {
            return;
        }

        WrcCustom1Data lastData = thisRun.getLast();

        StageRecording bestRun = bestRuns.get(new StageRecordKey(lastData));

        switch (lastData.getStageStatus()) {
            case StageResultStatus.NOT_FINISHED:
                break;
            case StageResultStatus.FINISHED:
                if (timeFasterThanRun(lastData, bestRun)) {
                    addToBestRuns(thisRun);
                }
                break;
            default:
                break;
        }
    }

    private void handleStageStarting() {
        dataRecorder.clearData();
    }

    public Optional<StageRecording> getBestRun(StageRecordKey key) {
        return Optional.ofNullable(bestRuns.get(key));
    }

    private boolean hasStageEnded() {
        return dataRecorder.getRecentlyAddedData(WrcSessionEndData.class, 0) != null;
    }

    private boolean hasStageStarted() {
        return dataRecorder.getRecentlyAddedData(WrcSessionStartData.class, 0) != null;
    }

    private boolean timeFasterThanRun(WrcCustom1Data data, @Nullable StageRecording run) {
        return run == null || data.getStageResultTime() < run.getStageResultTime();
    }

    private void addToBestRuns(List<WrcCustom1Data> run) {
        StageRecordKey key = new StageRecordKey(run.getLast());
        StageRecording newPB = new StageRecording(key,
                run.stream().map(WrcEssentialData::createFromCustomData).toList());
        bestRuns.put(newPB.getKey(), newPB);
        try {
            serializeDataOut(bestRuns);
        } catch (IOException e) {
            System.out.println("Error writing to file, couldn't save stage pbs: " + e);
        }
        lastUpdated = System.currentTimeMillis();
    }

    public long getStagesLastUpdated() {
        return lastUpdated;
    }
}
