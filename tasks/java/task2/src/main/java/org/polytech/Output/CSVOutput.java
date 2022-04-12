package org.polytech.Output;

import org.polytech.Utils.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVOutput implements Output {
    private final String fileName = "output.csv";
    private final String SEPARATOR = ",";

    private final String dir;

    public CSVOutput(String dir) {
        this.dir = dir;
    }

    @Override
    public void save(List<Double> list) throws IOException {
        Logger.log("Saving data to CSV file");

        new File(dir + File.separator + fileName).delete();

        FileWriter fileWriter = new FileWriter(dir + File.separator + fileName, true);
        for (Double s : list) {
            fileWriter.append(s.toString());
            fileWriter.append(SEPARATOR);
        }
        fileWriter.append("\n");
        fileWriter.flush();
        fileWriter.close();

        Logger.log("Data saved to CSV file");
    }
}
