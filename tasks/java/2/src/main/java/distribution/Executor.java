package main.java.distribution;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Executor {

    public Double[] param;
    public String type;
    public int n;
    public String pathToFile;

    public void start(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments passed to the program.");
        } else {
            Arrays.stream(args).forEach(this::argumentHandler);
            CreateDistribution createDistribution = new CreateDistribution();
            createDistribution.create(pathToFile, param, type, n);
        }
    }

    private void argumentHandler(String arg) {
        if (arg.contains("=")) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                String value = split[1].trim();
                switch (split[0].trim().toLowerCase()) {
                    case "type":
                        if (value.equalsIgnoreCase("normal")
                                || value.equalsIgnoreCase("uniform")
                                || value.equalsIgnoreCase("poisson")) {
                            type = value;
                            System.out.println("Selected " + type + " distribution.");
                        } else {
                            System.out.println("ERROR: Unknown distribution type.");
                            throw new IllegalArgumentException("Unknown distribution type.");
                        }
                        break;
                    case "n":
                        int intValue = Integer.parseInt(value);
                        if (intValue > 10000) {
                            n = intValue;
                            System.out.println("Selected " + n + " meaning.");
                        } else {
                            System.out.println("ERROR: Invalid count number.");
                            throw new IllegalArgumentException("Invalid count number.");
                        }
                        break;
                    case "path":
                        Path path = Paths.get(value);
                        File file = path.toFile();
                        if (!file.exists()) {
                            System.out.println("ERROR: Path not found.");
                            throw new IllegalArgumentException("Path not found.");
                        }
                        pathToFile = path.toString();
                        break;
                    case "parameter":
                        param = Arrays.stream(value.split(",")).map(Double::parseDouble).toArray(Double[]::new);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid arguments.");
                }
            } else {
                throw new IllegalArgumentException("Invalid string format.");
            }
        } else {
            throw new IllegalArgumentException("Invalid string format.");
        }
    }

}
