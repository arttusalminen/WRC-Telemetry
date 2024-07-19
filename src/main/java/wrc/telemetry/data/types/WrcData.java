package wrc.telemetry.data.types;

import generic.data.parsing.ParsedData;

import java.io.Serializable;

public abstract class WrcData implements ParsedData, Serializable {
    public static enum PacketId {
        SESSION_START("SESS"),
        SESSION_END("SESE"),
        SESSION_PAUSE("SESP"),
        SESSION_RESUME("SESR"),
        SESSION_UPDATE("SESU");

        private final String fourCC;

        private PacketId(String fourCC){
            this.fourCC = fourCC;
        }
        public String get4CC(){
            return fourCC;
        }
    }
}
