package ru.spbstu.distr.io;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class CsvExporter extends AbstractExporter {

    CSVWriter writer;

    public CsvExporter(Path outputFile) {
        super(outputFile);
    }

    @Override
    public void create() {

        super.create();

        try {
            writer = new CSVWriter(new FileWriter(outputFile.toString()));
        } catch (IOException e) {
            log.error("Failed to create file: {}", outputFile);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            log.error("Failed to close file: {}", outputFile);
        }
    }

    @Override
    public void write(DataEntry entry) {
        writer.writeNext(new String[]{String.valueOf(entry.id()), String.valueOf(entry.value())});
    }
}
