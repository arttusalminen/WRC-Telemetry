package wrc.telemetry.stages;

import wrc.telemetry.data.types.WrcCustom1Data;
import wrc.telemetry.overlays.DeltaOverlay;

import java.io.Serializable;
import java.util.Objects;

public class StageRecordKey implements Serializable {

    private final int country;
    private final int stage;
    private final int carClass;

    StageRecordKey(WrcCustom1Data data) {
        this.country = data.getLocationId();
        this.stage = data.getRouteId();
        this.carClass = data.getVehicleClassId();
    }

    public static StageRecordKey forData(WrcCustom1Data telemetryData) {
        return new StageRecordKey(telemetryData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageRecordKey that = (StageRecordKey) o;
        return country == that.country && stage == that.stage && carClass == that.carClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, stage, carClass);
    }
}
