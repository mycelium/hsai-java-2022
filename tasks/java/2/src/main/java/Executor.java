import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Executor {

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();

    private String fileAbsolute;
    private int count;
    private Double[] params;
    private String type;

    public void start(String... args) {
        if (args.length == 0) {
            exceptionHandler.stop("No arguments.");
        } else {
            Arrays.stream(args).forEach(this::argumentHandler);
            Writer writer = new Writer();
            writer.setFileData(fileAbsolute, count, params, type);
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
                            exceptionHandler.stop("Unknown distribution type.");
                        }
                        break;
                    case "count":
                        int intValue = Integer.parseInt(value);
                        if (intValue > 10000) {
                            count = intValue;
                            System.out.println("Selected " + count + " meaning.");
                        } else {
                            exceptionHandler.stop("Invalid count number.");
                        }
                        break;
                    case "path":
                        Path path = Paths.get(value);
                        File file = path.toFile();
                        if (!file.exists()) {
                            exceptionHandler.stop("Path not found.");
                        }
                        fileAbsolute = path.toString();
                        System.out.println("Selected " + fileAbsolute + " path.");
                        break;
                    case "parameter":
                        params = Arrays.stream(value.split(",")).map(Double::parseDouble).toArray(Double[]::new);
                        System.out.println("Selected " + Arrays.toString(params) + " parameter.");
                        break;
                    default:
                        exceptionHandler.stop("Invalid arguments.");
                }
            } else {
                exceptionHandler.stop("Invalid string format.");
            }
        } else {
            exceptionHandler.stop("Invalid string format.");
        }
    }

}
