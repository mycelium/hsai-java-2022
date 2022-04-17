package com.example.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter implements OutputWriter {
    private BufferedWriter bw;

    public CsvWriter(String directoryPath) throws IOException {
        bw = new BufferedWriter(new FileWriter(directoryPath));
    }

    public void write(List<Double> series) {
        try {
            bw.write(series.stream().map(String::valueOf).collect(Collectors.joining(",")));
            bw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
