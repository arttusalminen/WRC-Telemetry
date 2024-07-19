package wrc.telemetry.data;

import generic.data.parsing.ByteParseUtils;
import generic.data.parsing.DataParser;
import wrc.telemetry.data.types.*;
import wrc.telemetry.data.types.WrcData.PacketId;

import javax.annotation.CheckForNull;

public class WrcDataParser implements DataParser<WrcData> {
    @Override
    @CheckForNull
    public WrcData parse(byte[] b) {
        String fourCC = ByteParseUtils.getString(b, 0, 4).toUpperCase();
        if (PacketId.SESSION_UPDATE.get4CC().equals(fourCC)) {
            return WrcCustom1Data.parse(b);
        }
        else if (PacketId.SESSION_START.get4CC().equals(fourCC)) {
            return WrcSessionStartData.parse(b);
            }
        else if (PacketId.SESSION_END.get4CC().equals(fourCC)) {
            return WrcSessionEndData.parse(b);
        }
        else {
            System.out.println("Received unknown type of data: " + fourCC);
            return null;
        }
    }
}
