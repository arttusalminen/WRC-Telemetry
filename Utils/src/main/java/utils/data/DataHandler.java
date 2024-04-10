package utils.data;

public interface DataHandler<D extends ParsedData> {
    void handleData(D data);
}
