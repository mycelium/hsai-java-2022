package com.example.io;

import java.nio.file.Path;
import java.util.List;

public interface OutputWriter {
    public void write(List<Double> series);

    public Path getOutputFilePath();
}
