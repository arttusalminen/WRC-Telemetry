package wrc.telemetry.overlays;

import generic.data.recording.DataRecorder;
import wrc.telemetry.data.types.WrcData;
import wrc.telemetry.data.types.WrcEssentialData;
import wrc.telemetry.stages.StageContainer;
import generic.data.DataProvider;
import wrc.telemetry.data.types.WrcCustom1Data;
import generic.visuals.Overlay;
import wrc.telemetry.stages.StageRecordKey;
import wrc.telemetry.stages.StageRecording;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Overlay that shows the delta to the best run.
 */
public class DeltaOverlay implements Overlay {

    private static final int OVERLAY_WIDTH = 400;
    private static final int OVERLAY_HEIGHT = 200;

    private static final int TRENDS_SIZE = 15;

    long window;

    private final DataProvider<WrcData> dataProvider;
    private final StageContainer stageContainer;

    private JFrame frame;
    private JLabel label;

    private Optional<StageRecording> bestRun = Optional.empty();
    long bestRunLastUpdated = 0;
    private int currentIndex = 0;
    private float latestStageTime = 0;

    private Deque<Double> trends = new ArrayDeque<>(TRENDS_SIZE);

    private float lastDelta = 0;
    private float lastTimeStamp = 0;



    public DeltaOverlay(DataRecorder<WrcData> dataDataProvider) {
        this.dataProvider = dataDataProvider;
        this.stageContainer = new StageContainer(dataDataProvider);
    }

    @Override
    public void init() {



        // Swing
        frame = new JFrame("Delta Overlay");
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setBackground(Color.WHITE);
        frame.setAlwaysOnTop(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        frame.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        frame.setLocation((width - OVERLAY_WIDTH / 2) / 2,
                0);

        // Without this, the window is draggable from any non transparent
        // point, including points  inside textboxes.
        frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", true);

        frame.getContentPane().setLayout(new GridLayout());
        label = new JLabel();
        label.setOpaque(true);
        label.setText("     0.0 s      ");
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(label, "grow, debug");
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    public void render() {
        WrcCustom1Data latestTelemetryData = dataProvider.getRecentlyAddedData(WrcCustom1Data.class, 0);
        // Not on stage
        if (latestTelemetryData == null) {
            resetInternalValues();
        }

        // Stage restarted etc.
        else if (latestTelemetryData.getStageCurrentTime() < latestStageTime) {
            resetInternalValues();
            bestRun = stageContainer.getBestRun(StageRecordKey.forData(latestTelemetryData));
            bestRunLastUpdated = stageContainer.getStagesLastUpdated();
        }

        // Stage changed
        else if (bestRun.map(r -> !r.getKey().equals(StageRecordKey.forData(latestTelemetryData))).orElse(true)) {
            resetInternalValues();
            bestRun = stageContainer.getBestRun(StageRecordKey.forData(latestTelemetryData));
            bestRunLastUpdated = stageContainer.getStagesLastUpdated();
        }

        // Stage container updated its values
        else if (stageContainer.getStagesLastUpdated() > bestRunLastUpdated) {
            resetInternalValues();
            bestRun = stageContainer.getBestRun(StageRecordKey.forData(latestTelemetryData));
            bestRunLastUpdated = stageContainer.getStagesLastUpdated();
        }


        float currentDelta = 0f;

        List<WrcEssentialData> runData = bestRun.map(StageRecording::getRunData).orElse(List.of());

        for (int i = currentIndex; i < runData.size(); i++) {
            float stageProgress = latestTelemetryData.getStageProgress();
            if (runData.get(i).getStageProgress() > stageProgress) {
                currentDelta = latestTelemetryData.getStageCurrentTime() - runData.get(i).getStageCurrentTime();
                currentIndex = i;
                break;
            }
        }
        calculateTrend(currentDelta,
                latestTelemetryData != null ? latestTelemetryData.getStageCurrentTime() - lastTimeStamp : 0);
        lastTimeStamp = latestTelemetryData != null ? latestTelemetryData.getStageCurrentTime() : 0;
        latestStageTime = latestTelemetryData != null ? latestTelemetryData.getStageCurrentTime() : latestStageTime;

        label.setBackground(getColorFromTrend());
        label.setText(String.format("%.2f s", currentDelta));

        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void deinit() {

    }

    private void resetInternalValues() {
        bestRun = Optional.empty();
        currentIndex = 0;
        latestStageTime = 0;
        lastDelta = 0;
        lastTimeStamp = 0;
        trends.clear();
    }

    private void calculateTrend(float delta, float passedTime) {
        if (lastDelta == 0) {
            trends.add(0d);
        }
        else {
            double trendCandidate = (delta - lastDelta) / passedTime;
            if (Double.isNaN(trendCandidate)) {
                return;
            }
            else if (trendCandidate > 1) {
                trendCandidate = 1;
            }
            else if (trendCandidate < -1) {
                trendCandidate = -1;
            }
            trends.add(trendCandidate);

        }
        if (trends.size() > TRENDS_SIZE) {
            trends.poll();
        }
        lastDelta = delta;
    }

    private Color getColorFromTrend() {
        double trend = trends.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        int green = (int) (255 - (255 * Math.abs(Math.max(trend, 0))));
        int red = (int) (255 - (255 * Math.abs(Math.min(trend, 0))));
        int blue = 255 - (int) (255 * Math.min(Math.abs(trend), 1));
        return new Color(red, green, blue);
    }
}
