package spbstu.hsai.consoleGenerator.io.output;

import java.io.PrintWriter;

public class CsvOutput extends Output {

    private final PrintWriter writer;

    public CsvOutput(PrintWriter printWriter) {
        this.writer = printWriter;
    }

    @Override
    public void close() {
        writer.close();
    }

    @Override
    public void write(double value) {
        writer.println(value);
    }
}
