package wrc.telemetry.data.types;

import generic.data.parsing.ByteParseUtils;

public class WrcSessionEndData extends WrcData {

    private float StageCurrentTime;
    private double StageCurrentDistance;

    public float getStageCurrentTime() {
        return StageCurrentTime;
    }

    public double getStageCurrentDistance() {
        return StageCurrentDistance;
    }

    private WrcSessionEndData() {}

    public static WrcSessionEndData parse(byte[] b) {
        WrcSessionEndData p = new WrcSessionEndData();
        p.StageCurrentTime = ByteParseUtils.getFloat32(b, 24);
        p.StageCurrentDistance = ByteParseUtils.getDouble64(b, 28);
        return p;
    }
}
