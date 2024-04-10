package utils;

import utils.data.ParsedData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TelemetryData implements ParsedData {
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
    private float VehiclePositionX;
    private float VehiclePositionY;
    private float VehiclePositionZ;
    private float VehicleVelocityX;
    private float VehicleVelocityY;
    private float VehicleVelocityZ;
    private float VehicleAccelerationX;
    private float VehicleAccelerationY;
    private float VehicleAccelerationZ;
    private float VehicleLeftDirectionX;
    private float VehicleLeftDirectionY;
    private float VehicleLeftDirectionZ;
    private float VehicleForwardDirectionX;
    private float VehicleForwardDirectionY;
    private float VehicleForwardDirectionZ;
    private float VehicleUpDirectionX;
    private float VehicleUpDirectionY;
    private float VehicleUpDirectionZ;
    private float VehicleHubPositionBl;
    private float VehicleHubPositionBr;
    private float VehicleHubPositionFl;
    private float VehicleHubPositionFr;
    private float VehicleHubVelocityBl;
    private float VehicleHubVelocityBr;
    private float VehicleHubVelocityFl;
    private float VehicleHubVelocityFr;
    private float VehicleCpForwardSpeedBl;
    private float VehicleCpForwardSpeedBr;
    private float VehicleCpForwardSpeedFl;
    private float VehicleCpForwardSpeedFr;
    private float VehicleBrakeTemperatureBl;
    private float VehicleBrakeTemperatureBr;
    private float VehicleBrakeTemperatureFl;
    private float VehicleBrakeTemperatureFr;
    private float VehicleEngineRpmMax;
    private float VehicleEngineRpmIdle;
    private float VehicleEngineRpmCurrent;
    private float VehicleThrottle;
    private float VehicleBrake;
    private float VehicleClutch;
    private float VehicleSteering;
    private float VehicleHandbrake;
    private float StageCurrentTime;
    private float StageCurrentDistance;
    private float StageLength;

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

    public float getVehiclePositionX() {
        return VehiclePositionX;
    }

    public float getVehiclePositionY() {
        return VehiclePositionY;
    }

    public float getVehiclePositionZ() {
        return VehiclePositionZ;
    }

    public float getVehicleVelocityX() {
        return VehicleVelocityX;
    }

    public float getVehicleVelocityY() {
        return VehicleVelocityY;
    }

    public float getVehicleVelocityZ() {
        return VehicleVelocityZ;
    }

    public float getVehicleAccelerationX() {
        return VehicleAccelerationX;
    }

    public float getVehicleAccelerationY() {
        return VehicleAccelerationY;
    }

    public float getVehicleAccelerationZ() {
        return VehicleAccelerationZ;
    }

    public float getVehicleLeftDirectionX() {
        return VehicleLeftDirectionX;
    }

    public float getVehicleLeftDirectionY() {
        return VehicleLeftDirectionY;
    }

    public float getVehicleLeftDirectionZ() {
        return VehicleLeftDirectionZ;
    }

    public float getVehicleForwardDirectionX() {
        return VehicleForwardDirectionX;
    }

    public float getVehicleForwardDirectionY() {
        return VehicleForwardDirectionY;
    }

    public float getVehicleForwardDirectionZ() {
        return VehicleForwardDirectionZ;
    }

    public float getVehicleUpDirectionX() {
        return VehicleUpDirectionX;
    }

    public float getVehicleUpDirectionY() {
        return VehicleUpDirectionY;
    }

    public float getVehicleUpDirectionZ() {
        return VehicleUpDirectionZ;
    }

    public float getVehicleHubPositionBl() {
        return VehicleHubPositionBl;
    }

    public float getVehicleHubPositionBr() {
        return VehicleHubPositionBr;
    }

    public float getVehicleHubPositionFl() {
        return VehicleHubPositionFl;
    }

    public float getVehicleHubPositionFr() {
        return VehicleHubPositionFr;
    }

    public float getVehicleHubVelocityBl() {
        return VehicleHubVelocityBl;
    }

    public float getVehicleHubVelocityBr() {
        return VehicleHubVelocityBr;
    }

    public float getVehicleHubVelocityFl() {
        return VehicleHubVelocityFl;
    }

    public float getVehicleHubVelocityFr() {
        return VehicleHubVelocityFr;
    }

    public float getVehicleCpForwardSpeedBl() {
        return VehicleCpForwardSpeedBl;
    }

    public float getVehicleCpForwardSpeedBr() {
        return VehicleCpForwardSpeedBr;
    }

    public float getVehicleCpForwardSpeedFl() {
        return VehicleCpForwardSpeedFl;
    }

    public float getVehicleCpForwardSpeedFr() {
        return VehicleCpForwardSpeedFr;
    }

    public float getVehicleBrakeTemperatureBl() {
        return VehicleBrakeTemperatureBl;
    }

    public float getVehicleBrakeTemperatureBr() {
        return VehicleBrakeTemperatureBr;
    }

    public float getVehicleBrakeTemperatureFl() {
        return VehicleBrakeTemperatureFl;
    }

    public float getVehicleBrakeTemperatureFr() {
        return VehicleBrakeTemperatureFr;
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

    public float getStageCurrentTime() {
        return StageCurrentTime;
    }

    public float getStageCurrentDistance() {
        return StageCurrentDistance;
    }

    public float getStageLength() {
        return StageLength;
    }

    private TelemetryData() {
    }

    @Override
    public String toString() {
        return "TelemetryData{" +
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
                ", VehiclePositionX=" + VehiclePositionX +
                ", VehiclePositionY=" + VehiclePositionY +
                ", VehiclePositionZ=" + VehiclePositionZ +
                ", VehicleVelocityX=" + VehicleVelocityX +
                ", VehicleVelocityY=" + VehicleVelocityY +
                ", VehicleVelocityZ=" + VehicleVelocityZ +
                ", VehicleAccelerationX=" + VehicleAccelerationX +
                ", VehicleAccelerationY=" + VehicleAccelerationY +
                ", VehicleAccelerationZ=" + VehicleAccelerationZ +
                ", VehicleLeftDirectionX=" + VehicleLeftDirectionX +
                ", VehicleLeftDirectionY=" + VehicleLeftDirectionY +
                ", VehicleLeftDirectionZ=" + VehicleLeftDirectionZ +
                ", VehicleForwardDirectionX=" + VehicleForwardDirectionX +
                ", VehicleForwardDirectionY=" + VehicleForwardDirectionY +
                ", VehicleForwardDirectionZ=" + VehicleForwardDirectionZ +
                ", VehicleUpDirectionX=" + VehicleUpDirectionX +
                ", VehicleUpDirectionY=" + VehicleUpDirectionY +
                ", VehicleUpDirectionZ=" + VehicleUpDirectionZ +
                ", VehicleHubPositionBl=" + VehicleHubPositionBl +
                ", VehicleHubPositionBr=" + VehicleHubPositionBr +
                ", VehicleHubPositionFl=" + VehicleHubPositionFl +
                ", VehicleHubPositionFr=" + VehicleHubPositionFr +
                ", VehicleHubVelocityBl=" + VehicleHubVelocityBl +
                ", VehicleHubVelocityBr=" + VehicleHubVelocityBr +
                ", VehicleHubVelocityFl=" + VehicleHubVelocityFl +
                ", VehicleHubVelocityFr=" + VehicleHubVelocityFr +
                ", VehicleCpForwardSpeedBl=" + VehicleCpForwardSpeedBl +
                ", VehicleCpForwardSpeedBr=" + VehicleCpForwardSpeedBr +
                ", VehicleCpForwardSpeedFl=" + VehicleCpForwardSpeedFl +
                ", VehicleCpForwardSpeedFr=" + VehicleCpForwardSpeedFr +
                ", VehicleBrakeTemperatureBl=" + VehicleBrakeTemperatureBl +
                ", VehicleBrakeTemperatureBr=" + VehicleBrakeTemperatureBr +
                ", VehicleBrakeTemperatureFl=" + VehicleBrakeTemperatureFl +
                ", VehicleBrakeTemperatureFr=" + VehicleBrakeTemperatureFr +
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
                ", StageLength=" + StageLength +
                '}';
    }

    public static TelemetryData parse(byte[] b) {
        TelemetryData p = new TelemetryData();
        p.PacketUid =  getLong(b, 0, 8);
        p.GameTotalTime = getFloat(b, 8, 12);
        p.GameDeltaTime = getFloat(b, 12, 16);
        p.GameFrameCount = getLong(b, 16, 24);
        p.ShiftlightsFraction = getFloat(b, 24, 28);
        p.ShiftlightsRpmStart = getFloat(b, 28, 32);
        p.ShiftlightsRpmEnd = getFloat(b, 32, 36);
        p.ShiftlightsRpmValid = b[36] != 0;
        p.VehicleGearIndex = b[37];
        p.VehicleGearIndexNeutral = b[38];
        p.VehicleGearIndexReverse = b[39];
        p.VehicleGearMaximum = b[40];
        p.VehicleSpeed = getFloat(b, 41, 45);
        p.VehicleTransmissionSpeed = getFloat(b, 45, 49);
        p.VehiclePositionX = getFloat(b, 49, 53);
        p.VehiclePositionY = getFloat(b, 53, 57);
        p.VehiclePositionZ = getFloat(b, 57, 61);
        p.VehicleVelocityX = getFloat(b, 61, 65);
        p.VehicleVelocityY = getFloat(b, 65, 69);
        p.VehicleVelocityZ = getFloat(b, 69, 73);
        p.VehicleAccelerationX = getFloat(b, 73, 77);
        p.VehicleAccelerationY = getFloat(b, 77, 81);
        p.VehicleAccelerationZ = getFloat(b, 81, 85);
        p.VehicleLeftDirectionX = getFloat(b, 85, 89);
        p.VehicleLeftDirectionY = getFloat(b, 89, 93);
        p.VehicleLeftDirectionZ = getFloat(b, 93, 97);
        p.VehicleForwardDirectionX = getFloat(b, 97, 101);
        p.VehicleForwardDirectionY = getFloat(b, 101, 105);
        p.VehicleForwardDirectionZ = getFloat(b, 105, 109);
        p.VehicleUpDirectionX = getFloat(b, 109, 113);
        p.VehicleUpDirectionY = getFloat(b, 113, 117);
        p.VehicleUpDirectionZ = getFloat(b, 117, 121);
        p.VehicleHubPositionBl = getFloat(b, 121, 125);
        p.VehicleHubPositionBr = getFloat(b, 125, 129);
        p.VehicleHubPositionFl = getFloat(b, 129, 133);
        p.VehicleHubPositionFr = getFloat(b, 133, 137);
        p.VehicleHubVelocityBl = getFloat(b, 137, 141);
        p.VehicleHubVelocityBr = getFloat(b, 141, 145);
        p.VehicleHubVelocityFl = getFloat(b, 145, 149);
        p.VehicleHubVelocityFr = getFloat(b, 149, 153);
        p.VehicleCpForwardSpeedBl = getFloat(b, 153, 157);
        p.VehicleCpForwardSpeedBr = getFloat(b, 157, 161);
        p.VehicleCpForwardSpeedFl = getFloat(b, 161, 165);
        p.VehicleCpForwardSpeedFr = getFloat(b, 165, 169);
        p.VehicleBrakeTemperatureBl = getFloat(b, 169, 173);
        p.VehicleBrakeTemperatureBr = getFloat(b, 173, 177);
        p.VehicleBrakeTemperatureFl = getFloat(b, 177, 181);
        p.VehicleBrakeTemperatureFr = getFloat(b, 181, 185);
        p.VehicleEngineRpmMax = getFloat(b, 185, 189);
        p.VehicleEngineRpmIdle = getFloat(b, 189, 193);
        p.VehicleEngineRpmCurrent = getFloat(b, 193, 197);
        p.VehicleThrottle = getFloat(b, 197, 201);
        p.VehicleBrake = getFloat(b, 201, 205);
        p.VehicleClutch = getFloat(b, 205, 209);
        p.VehicleSteering = getFloat(b, 209, 213);
        p.VehicleHandbrake = getFloat(b, 213, 217);
        p.StageCurrentTime = getFloat(b, 217, 221);
        p.StageCurrentDistance = getFloat(b, 221, 229);
        p.StageLength = getFloat(b, 229, 237);
        return p;
    }


    private static byte[] extractBytes(byte[] b, int start, int end) {
        if (b.length < end) {
            throw new IllegalArgumentException("Byte array does not have enough bytes.");
        }

        byte[] extractedBytes = new byte[end - start];
        System.arraycopy(b, start, extractedBytes, 0, end - start);
        return extractedBytes;
    }

    private static float getFloat(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getFloat();
    }

    private static long getLong(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getLong();
    }
}
