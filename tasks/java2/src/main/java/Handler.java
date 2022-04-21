import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Handler {

    private String fileAbsolute;
    private int count;
    private Double[] params;
    private String type;

    public void start(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments passed to the program.");
        } else {
            Arrays.stream(args).forEach(this::argumentHandler);
            GenerateAndWrite generateAndWrite = new GenerateAndWrite();
            generateAndWrite.generateAndWrite(fileAbsolute, count, params, type);
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
                    case "count":
                        int intValue = Integer.parseInt(value);
                        if (intValue > 10000) {
                            count = intValue;
                            System.out.println("Selected " + count + " meaning.");
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
                        fileAbsolute = path.toString();
                        break;
                    case "parameter":
                        params = Arrays.stream(value.split(",")).map(Double::parseDouble).toArray(Double[]::new);
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
