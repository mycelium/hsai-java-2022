package com.example.out;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public record CSVWriter(String path) implements Output {
    private static Logger logger = logger = Logger.getLogger(CSVWriter.class.getName());

    public CSVWriter(String path) {
        this.path = path + "/result.csv";
    }

    @Override
    public void write(List<Double> points) {
        try (PrintWriter writer = new PrintWriter(path)) {

            String string = points.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            writer.write(string);
            logger.info("CSV data writing successful");

        } catch (FileNotFoundException e) {
            logger.severe(e.getMessage());
        }
    }
}
