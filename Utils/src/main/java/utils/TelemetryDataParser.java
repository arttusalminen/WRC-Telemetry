package utils;

import utils.data.DataParser;

public class TelemetryDataParser implements DataParser<TelemetryData> {
    @Override
    public TelemetryData parse(byte[] b) {
        return TelemetryData.parse(b);
    }
}
