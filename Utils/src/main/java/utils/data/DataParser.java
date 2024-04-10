package utils.data;

public interface DataParser<D extends ParsedData> {
    D parse(byte[] b);
}
