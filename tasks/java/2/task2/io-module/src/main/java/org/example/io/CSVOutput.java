package org.example.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.example.log.Log;

public class CSVOutput implements Output {

    private final Log log = new Log(this.getClass());

    private final String path;

    public CSVOutput(String path) {
        this.path = path;
    }

    @Override
    public void save(List<Double> data) {
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            log.info("Start saving data!");
            fileWriter.append(
                data.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
                .append("\n");
            log.info("Ending saving data!");
        } catch (IOException e) {
            log.error("Error writing in CSV!");
        }
    }
}
