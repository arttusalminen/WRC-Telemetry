package wrc.telemetry.data;

import generic.data.parsing.DataParser;
import wrc.telemetry.data.types.*;

import javax.annotation.CheckForNull;

public class WrcDataParser implements DataParser<WrcData> {
    @Override
    @CheckForNull
    public WrcData parse(byte[] b) {
        if (b.length == 243)
            return WrcCustom1Data.parse(b);
        else if (b.length == 27)
            return WrcSessionStartData.parse(b);
        else if (b.length == 12)
            return WrcSessionEndData.parse(b);
        else
            return null;
    }
}
