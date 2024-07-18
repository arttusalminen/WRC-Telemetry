package wrc.telemetry;

import generic.data.recording.DataRecorder;
import wrc.telemetry.data.types.WrcCustom1Data;

public class StageContainer {

    private final DataRecorder<WrcCustom1Data> dataRecorder;

    public StageContainer(DataRecorder<WrcCustom1Data> dataRecorder) {
        this.dataRecorder = dataRecorder;
    }


}
