package wrc.telemetry.stages;

import wrc.telemetry.data.types.WrcCustom1Data;
import wrc.telemetry.data.types.WrcEssentialData;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Immutable
public class StageRecording implements Serializable {

    private final StageRecordKey key;

    private final List<WrcEssentialData> runData;

    public StageRecording(StageRecordKey key, List<WrcEssentialData> runData) {
        this.key = key;
        this.runData = runData;
    }

    public StageRecordKey getKey() {
        return key;
    }

    public List<WrcEssentialData> getRunData() {
        return runData;
    }

    public float getStageResultTime() {
        return runData.getLast().getStageResultTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageRecording that = (StageRecording) o;
        return Objects.equals(key, that.key) && Objects.equals(runData, that.runData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, runData);
    }
}
