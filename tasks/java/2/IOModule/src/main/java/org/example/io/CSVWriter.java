package org.example.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.logging.Logger;
public class CSVWriter implements Writer {
    private static Logger logger = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(DBWriter.class.getName());
    }
    private final String path;

    public  CSVWriter(String path){
        this.path = path + "/test.csv";
    }
    @Override
    public void write(List<Double> points) {
        try (PrintWriter writer = new PrintWriter(path)) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(
                    points.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","))
            );

            writer.write(stringBuilder.toString());
            logger.info("Writing to csv was successful");

        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
