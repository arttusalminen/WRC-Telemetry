package wrc.telemetry.data.types;

import com.google.common.base.Preconditions;
import generic.data.parsing.ByteParseUtils;
import wrc.telemetry.data.UIDs.StageResultStatus;

public class WrcCustom1Data extends WrcEssentialData {
    private static final String PACKET_4CC = "SESU";

    private long PacketUid;
    private float GameTotalTime;
    private float GameDeltaTime;
    private long GameFrameCount;
    private float ShiftlightsFraction;
    private float ShiftlightsRpmStart;
    private float ShiftlightsRpmEnd;
    private boolean ShiftlightsRpmValid;
    private int VehicleGearIndex;
    private int VehicleGearIndexNeutral;
    private int VehicleGearIndexReverse;
    private int VehicleGearMaximum;
    private float VehicleSpeed;
    private float VehicleTransmissionSpeed;
    private float VehicleEngineRpmMax;
    private float VehicleEngineRpmIdle;
    private float VehicleEngineRpmCurrent;
    private float VehicleThrottle;
    private float VehicleBrake;
    private float VehicleClutch;
    private float VehicleSteering;
    private float VehicleHandbrake;
    private double StageCurrentDistance;
    private double StageLength;
    private int LocationId;
    private int RouteId;
    private int VehicleClassId;

    public long getPacketUid() {
        return PacketUid;
    }

    public float getGameTotalTime() {
        return GameTotalTime;
    }

    public float getGameDeltaTime() {
        return GameDeltaTime;
    }

    public long getGameFrameCount() {
        return GameFrameCount;
    }

    public float getShiftlightsFraction() {
        return ShiftlightsFraction;
    }

    public float getShiftlightsRpmStart() {
        return ShiftlightsRpmStart;
    }

    public float getShiftlightsRpmEnd() {
        return ShiftlightsRpmEnd;
    }

    public boolean isShiftlightsRpmValid() {
        return ShiftlightsRpmValid;
    }

    public int getVehicleGearIndex() {
        return VehicleGearIndex;
    }

    public int getVehicleGearIndexNeutral() {
        return VehicleGearIndexNeutral;
    }

    public int getVehicleGearIndexReverse() {
        return VehicleGearIndexReverse;
    }

    public int getVehicleGearMaximum() {
        return VehicleGearMaximum;
    }

    public float getVehicleSpeed() {
        return VehicleSpeed;
    }

    public float getVehicleTransmissionSpeed() {
        return VehicleTransmissionSpeed;
    }

    public float getVehicleEngineRpmMax() {
        return VehicleEngineRpmMax;
    }

    public float getVehicleEngineRpmIdle() {
        return VehicleEngineRpmIdle;
    }

    public float getVehicleEngineRpmCurrent() {
        return VehicleEngineRpmCurrent;
    }

    public float getVehicleThrottle() {
        return VehicleThrottle;
    }

    public float getVehicleBrake() {
        return VehicleBrake;
    }

    public float getVehicleClutch() {
        return VehicleClutch;
    }

    public float getVehicleSteering() {
        return VehicleSteering;
    }

    public float getVehicleHandbrake() {
        return VehicleHandbrake;
    }

    public double getStageCurrentDistance() {
        return StageCurrentDistance;
    }

    public double getStageLength() {
        return StageLength;
    }

    public int getLocationId() {
        return LocationId;
    }

    public int getRouteId() {
        return RouteId;
    }

    public int getVehicleClassId() {
        return VehicleClassId;
    }

    private WrcCustom1Data() {
    }

    @Override
    public String toString() {
        return "WrcCustom1Data{" +
                "PacketUid=" + PacketUid +
                ", GameTotalTime=" + GameTotalTime +
                ", GameDeltaTime=" + GameDeltaTime +
                ", GameFrameCount=" + GameFrameCount +
                ", ShiftlightsFraction=" + ShiftlightsFraction +
                ", ShiftlightsRpmStart=" + ShiftlightsRpmStart +
                ", ShiftlightsRpmEnd=" + ShiftlightsRpmEnd +
                ", ShiftlightsRpmValid=" + ShiftlightsRpmValid +
                ", VehicleGearIndex=" + VehicleGearIndex +
                ", VehicleGearIndexNeutral=" + VehicleGearIndexNeutral +
                ", VehicleGearIndexReverse=" + VehicleGearIndexReverse +
                ", VehicleGearMaximum=" + VehicleGearMaximum +
                ", VehicleSpeed=" + VehicleSpeed +
                ", VehicleTransmissionSpeed=" + VehicleTransmissionSpeed +
                ", VehicleEngineRpmMax=" + VehicleEngineRpmMax +
                ", VehicleEngineRpmIdle=" + VehicleEngineRpmIdle +
                ", VehicleEngineRpmCurrent=" + VehicleEngineRpmCurrent +
                ", VehicleThrottle=" + VehicleThrottle +
                ", VehicleBrake=" + VehicleBrake +
                ", VehicleClutch=" + VehicleClutch +
                ", VehicleSteering=" + VehicleSteering +
                ", VehicleHandbrake=" + VehicleHandbrake +
                ", StageCurrentTime=" + StageCurrentTime +
                ", StageCurrentDistance=" + StageCurrentDistance +
                ", StageResultTime=" + StageResultTime +
                ", StageResultStatus=" + StageStatus +
                ", StageProgress=" + StageProgress +
                ", StageLength=" + StageLength +
                ", LocationId=" + LocationId +
                ", RouteId=" + RouteId +
                ", VehicleClassId=" + VehicleClassId +
                '}';
    }

    public static WrcCustom1Data parse(byte[] b) {
        Preconditions.checkArgument(ByteParseUtils.getString(b, 0, 4).toUpperCase().equals(PacketId.SESSION_UPDATE.get4CC()),
                "Invalid " +
                "packet 4CC. " +
                "Expected: " + PACKET_4CC + " Actual: " + ByteParseUtils.getString(b, 0, 4));

        WrcCustom1Data p = new WrcCustom1Data();
        p.PacketUid = ByteParseUtils.getLong64(b, 4);
        p.GameTotalTime = ByteParseUtils.getFloat32(b, 12);
        p.GameDeltaTime = ByteParseUtils.getFloat32(b, 16);
        p.GameFrameCount = ByteParseUtils.getLong64(b, 20);
        p.ShiftlightsFraction = ByteParseUtils.getFloat32(b, 28);
        p.ShiftlightsRpmStart = ByteParseUtils.getFloat32(b, 32);
        p.ShiftlightsRpmEnd = ByteParseUtils.getFloat32(b, 36);
        p.ShiftlightsRpmValid = ByteParseUtils.getBoolean(b, 40);
        p.VehicleGearIndex = ByteParseUtils.getByte8(b, 41);
        p.VehicleGearIndexNeutral = ByteParseUtils.getByte8(b, 42);
        p.VehicleGearIndexReverse = ByteParseUtils.getByte8(b, 43);
        p.VehicleGearMaximum = ByteParseUtils.getByte8(b, 44);
        p.VehicleSpeed = ByteParseUtils.getFloat32(b, 45);
        p.VehicleTransmissionSpeed = ByteParseUtils.getFloat32(b, 49);
        p.VehicleEngineRpmMax = ByteParseUtils.getFloat32(b, 53);
        p.VehicleEngineRpmIdle = ByteParseUtils.getFloat32(b, 57);
        p.VehicleEngineRpmCurrent = ByteParseUtils.getFloat32(b, 61);
        p.VehicleThrottle = ByteParseUtils.getFloat32(b, 65);
        p.VehicleBrake = ByteParseUtils.getFloat32(b, 69);
        p.VehicleClutch = ByteParseUtils.getFloat32(b, 73);
        p.VehicleSteering = ByteParseUtils.getFloat32(b, 77);
        p.VehicleHandbrake = ByteParseUtils.getFloat32(b, 81);
        p.StageCurrentTime = ByteParseUtils.getFloat32(b, 85);
        p.StageCurrentDistance = ByteParseUtils.getDouble64(b, 89);
        p.StageResultTime = ByteParseUtils.getFloat32(b, 97);
        p.StageStatus = StageResultStatus.fromId(ByteParseUtils.getByte8(b, 101));
        p.StageProgress = ByteParseUtils.getFloat32(b, 102);
        p.StageLength = ByteParseUtils.getDouble64(b, 106);
        p.LocationId = ByteParseUtils.getShort16(b, 114);
        p.RouteId = ByteParseUtils.getShort16(b, 116);
        p.VehicleClassId = ByteParseUtils.getShort16(b, 118);
        return p;
    }
}
