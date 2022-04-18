package com.example.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter implements OutputWriter {
    private final BufferedWriter bw;
    private final Path outPath;

    public CsvWriter(String directoryPath) throws Exception {
        outPath = Paths.get(directoryPath + "/rand.csv").toAbsolutePath();
        bw = Files.newBufferedWriter(outPath);
    }

    public void write(List<Double> series) {
        try {
            bw.write(series.stream().map(String::valueOf).collect(Collectors.joining(",")));
            bw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Path getOutputFilePath() {
        return outPath;
    }
}
