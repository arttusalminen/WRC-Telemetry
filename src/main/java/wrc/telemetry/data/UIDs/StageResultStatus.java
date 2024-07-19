package wrc.telemetry.data.UIDs;

public enum StageResultStatus {
    NOT_FINISHED(0),
    FINISHED(1),
    TIMED_OUT_STAGE(2),
    TERMINALLY_DAMAGED(3),
    RETIRED(4),
    DISQUALIFIED(5),
    UNKNOWN(6);

    private final int id;

    StageResultStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static StageResultStatus fromId(int id) {
        for (StageResultStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
