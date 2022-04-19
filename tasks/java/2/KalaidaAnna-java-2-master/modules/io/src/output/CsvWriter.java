package output;

import java.io.PrintWriter;

public class CsvWriter implements OutputWriter {

    private final PrintWriter writer;
    private boolean isFirstValue = true;

    public CsvWriter(PrintWriter printWriter) {
        this.writer = printWriter;
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }

    @Override
    public <T extends Number> void write(T value) {
        if (isFirstValue) {
            writer.println("value");
            isFirstValue = false;
        }
        writer.println(value);
    }
}
