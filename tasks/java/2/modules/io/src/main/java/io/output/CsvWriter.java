package io.output;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvWriter implements OutputWriter {
    private final PrintWriter writer;

    private CsvWriter(Path file) throws IOException {
        writer = new PrintWriter(Files.newBufferedWriter(file));
        writer.println("value");
    }

    public static CsvWriter of(Path file) throws IOException {
        return new CsvWriter(file);
    }

    @Override
    public void accept(Double value) {
        writer.println(value);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
