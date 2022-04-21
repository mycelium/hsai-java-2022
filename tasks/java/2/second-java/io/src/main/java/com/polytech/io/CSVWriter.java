package com.polytech.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

public class CSVWriter implements Writer{

    private static final Logger logger = new ConsoleLogger(CSVWriter.class.getName());

    private final String file;

    public CSVWriter(String file) {
        this.file = file;
    }

    @Override
    public void write(List<Double> data) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            data.forEach(x -> {
                try {
                    fileWriter.append(String.valueOf(x)).append(",");
                } catch (IOException e) {
                    logger.error(e);
                }
            });
            fileWriter.append("\n");
            logger.trace("wrote data in csv");
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
