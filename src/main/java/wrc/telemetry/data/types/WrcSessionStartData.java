package wrc.telemetry.data.types;

import generic.data.parsing.ByteParseUtils;

import javax.annotation.CheckForNull;

public class WrcSessionStartData extends WrcData {

    private float shiftlights_rpm_start;
    private float shiftlights_rpm_end;
    private int vehicle_gear_index_neutral;
    private int vehicle_gear_index_reverse;
    private int vehicle_gear_maximum;
    private float vehicle_engine_rpm_max;
    private float vehicle_engine_rpm_idle;
    private double stage_length;

    public float getShiftlights_rpm_start() {
        return shiftlights_rpm_start;
    }

    public float getShiftlights_rpm_end() {
        return shiftlights_rpm_end;
    }

    public int getVehicle_gear_index_neutral() {
        return vehicle_gear_index_neutral;
    }

    public int getVehicle_gear_index_reverse() {
        return vehicle_gear_index_reverse;
    }

    public int getVehicle_gear_maximum() {
        return vehicle_gear_maximum;
    }

    public float getVehicle_engine_rpm_max() {
        return vehicle_engine_rpm_max;
    }

    public float getVehicle_engine_rpm_idle() {
        return vehicle_engine_rpm_idle;
    }

    public double getStage_length() {
        return stage_length;
    }

    private WrcSessionStartData() {}

    @CheckForNull
    public static WrcSessionStartData parse(byte[] b) {
        WrcSessionStartData p = new WrcSessionStartData();
        p.shiftlights_rpm_start = ByteParseUtils.getFloat32(b, 24);
        p.shiftlights_rpm_end = ByteParseUtils.getFloat32(b, 28);
        p.vehicle_gear_index_neutral = ByteParseUtils.getByte8(b, 32);
        p.vehicle_gear_index_reverse = ByteParseUtils.getByte8(b, 33);
        p.vehicle_gear_maximum = ByteParseUtils.getByte8(b, 34);
        p.vehicle_engine_rpm_max = ByteParseUtils.getFloat32(b, 35);
        p.vehicle_engine_rpm_idle = ByteParseUtils.getFloat32(b, 39);
        p.stage_length = ByteParseUtils.getDouble64(b, 43);
        return p;
    }
}
