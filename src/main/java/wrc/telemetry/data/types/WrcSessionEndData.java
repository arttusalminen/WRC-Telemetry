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
        p.StageCurrentTime = ByteParseUtils.getFloat(b, 0, 4);
        p.StageCurrentDistance = ByteParseUtils.getDouble(b, 4, 12);
        return p;
    }
}
