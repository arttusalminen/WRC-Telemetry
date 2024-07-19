package wrc.telemetry.data.types;

import wrc.telemetry.data.UIDs.StageResultStatus;

public class WrcEssentialData extends WrcData {

    protected float StageCurrentTime;
    protected float StageResultTime;
    protected StageResultStatus StageStatus;
    protected float StageProgress;

    public float getStageCurrentTime() {
        return StageCurrentTime;
    }

    public float getStageResultTime() {
        return StageResultTime;
    }

    public StageResultStatus getStageStatus() {
        return StageStatus;
    }

    public float getStageProgress() {
        return StageProgress;
    }

    public static WrcEssentialData createFromCustomData(WrcCustom1Data data) {
        WrcEssentialData d = new WrcEssentialData();
        d.StageCurrentTime = data.getStageCurrentTime();
        d.StageResultTime = data.getStageResultTime();
        d.StageStatus = data.getStageStatus();
        d.StageProgress = data.getStageProgress();
        return d;
    }

}
