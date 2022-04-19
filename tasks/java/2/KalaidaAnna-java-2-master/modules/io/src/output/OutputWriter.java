package output;

public interface OutputWriter extends AutoCloseable {

    <T extends Number> void write(T value);
}
