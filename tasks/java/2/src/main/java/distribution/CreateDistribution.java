package main.java.distribution;

import main.java.distribution.Distributions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class CreateDistribution {
    public void create(String pathToFile, Double[] param, String type, int n){
        Path path = Paths.get(pathToFile);
        File file = new File(path + File.separator + "output.csv");
        if (file.exists()) {
            file.delete();
            System.out.println("Success delete old file.");
        }
        try {
            file.createNewFile();
            System.out.println("Success create output file.");
            FileWriter fw = new FileWriter(file);
            generateDistr(n, param, type, fw);
            System.out.println("Success generated file to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("‼️ " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void generateDistr(int n, Double[] param, String type, FileWriter fileWriter) throws IOException {
        Distributions distributions;// = new Distributions(param1, param2);
        List<Double> createDistr;
        boolean flag = false;
        logSelect(type, flag, null);

        switch (type) {
            case "normal": {
                System.out.println("Normal Distribution");
                distributions = new Distributions(param[0], param[1]);
                createDistr = distributions.normalDistribution(n);
                logSelect(type, flag, Collections.singletonList(createDistr));
                break;
            }
            case "poisson": {
                System.out.println("Poisson Distribution");
                distributions = new Distributions(param[0], 0.0);
                createDistr = distributions.poissonDistribution(n);
                logSelect(type, flag, Collections.singletonList(createDistr));
                break;
            }
            case "uniform": {
                System.out.println("Uniform Distribution");
                distributions = new Distributions(param[0], param[1]);
                createDistr = distributions.uniformDistribution(n);
                logSelect(type, flag, Collections.singletonList(createDistr));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value!");
        }
        writeDistr(createDistr, fileWriter);
    }

    public void writeDistr(List<Double> distr, FileWriter fileWriter) throws IOException {
        distr.forEach(e -> {
            try {
                fileWriter.append(e.toString()).append("\n");
            } catch (IOException ee) {
                System.out.println("‼️ " + ee.getMessage());
                ee.printStackTrace();
            }
        });
        fileWriter.close();
    }

    private void logSelect(String value, boolean flag, List<Object> list) {
        if (flag) {
            if (list.size() != 0) {
                System.out.println("Distribution " + value + " successfully generated.");
            } else {
                stop("Error generating " + value + " distribution.");
            }
        } else {
            System.out.println("Beginning of " + value + " distribution generation.");
        }
    }

    public void stop(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m");
        System.exit(-1);
    }
}
