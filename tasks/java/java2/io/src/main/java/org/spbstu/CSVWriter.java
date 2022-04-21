package org.spbstu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter implements Writer {

    private static final Logger log = Logger.getLogger(CSVWriter.class.getName());

    private final String file;

    public CSVWriter(String file) { this.file = file; }

    @Override
    public void write(List<Double> values) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            values.forEach(x -> {
                try {
                    fileWriter.append(String.valueOf(x)).append(",");
                } catch (IOException e) {
                    log.warning("Error writing value to CSV!");
                }
            });
            log.info("Wrote values to CSV");
        } catch (IOException e) {
            log.warning("Error writing values to CSV!");
        }
    }

}
