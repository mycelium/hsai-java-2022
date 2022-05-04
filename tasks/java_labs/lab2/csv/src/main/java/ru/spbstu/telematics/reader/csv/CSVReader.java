package ru.spbstu.telematics.reader.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbstu.telematics.variables.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    private static Logger logger = LogManager.getLogger(Variable.class);

    private Path file;

    public CSVReader(String file) {
        logger.info("Create CSVReader for file: \"" + file + "\"");
        this.file = Path.of(file);
    }

    public List<Variable<Double>> readAllDistribution() throws IOException {
        BufferedReader inputStream = Files.newBufferedReader(file);
        logger.info("Start reading table from csv file: " + file.toString());
        String[] names = getNames(inputStream.readLine());
        List<Variable<Double>> data = new ArrayList<>(names.length);
        for (int i = 0; i < names.length; i++) {
            data.add(new Variable<>(names[i]));
        }
        List<Double> line;
        while ((line = readLine(inputStream)) != null) {
            for (int i = 0; i < line.size(); i++) {
                data.get(i).add(line.get(i));
            }
        }
        inputStream.close();
        logger.info("Read and return " + data.size() + " samples");
        logger.info("In every sample " + data.stream().findAny().orElse(new Variable<>("")).size() + " values");
        return data;
    }

    private String[] getNames(String names) {
        String[] tmp = names.split("\"\\s*?,\\s*\"");
        tmp[0] = tmp[0].substring(1);
        tmp[tmp.length - 1] = tmp[tmp.length - 1].substring(0, tmp[tmp.length - 1].length() - 1);
        logger.info("Read " + tmp.length + " names from " + file.toString());
        return tmp;
    }

    private List<Double> readLine(BufferedReader inputStream) throws IOException {
        String str = inputStream.readLine();
        if (str == null) {
            return null;
        }
        return Arrays.stream(str.split(","))
                .mapToDouble(Double::parseDouble)
                .boxed()
                .collect(Collectors.toList());
    }
}
