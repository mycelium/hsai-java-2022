import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Process {

    String filePath;
    int cnt;
    Double[] params;
    String type;

    public void doProcess(String[] args) {
        if (args.length == 0) {
            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "No arguments were found!");
            System.exit(-1);
        }
            Arrays.stream(args).forEach(this::processingArgs);
            DoGeneration doGeneration = new DoGeneration();
            doGeneration.doGeneration(filePath, cnt, params, type);
    }

    private void processingArgs(String arg) {
        if (arg.contains("=")) {
            String[] arr_split = arg.split("=");
            if (arr_split.length == 2) {
                String value = arr_split[1].replaceAll("\s","").toLowerCase();
                switch (arr_split[0].replaceAll("\s","").toLowerCase()) {
                    case "type":
                        switch (value) {
                            case "normal": {
                                type = value;
                                break;
                            }
                            case "uniform": {
                                type = value;
                                break;
                            }
                            case "poisson": {
                                type = value;
                                break;
                            }
                            default: {
                                System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Unknown distribution type!");
                                System.exit(-1);
                            }
                        }
                        break;
                    case "cnt":
                        try {
                            int intCnt = Integer.parseInt(value);
                            if (intCnt > 10000) {
                                cnt = intCnt;
                                System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Number of values equals to " + cnt);
                            } else {
                                System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid count number!");
                                System.exit(-1);
                            }
                        } catch (NumberFormatException exc) {
                            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid count number!");
                            System.exit(-1);
                        }
                        break;
                    case "path":
                        Path path = Paths.get(value);
                        File file = path.toFile();
                        if (!file.exists()) {
                            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Path not found!");
                            System.exit(-1);
                        }
                        filePath = path.toString();
                        System.out.println(Main.infoColor +  "INFORMATION: " + Main.resetColor + "File path: " + filePath);
                        break;
                    case "params":
                        try {
                            Double[] tempParams = Arrays.stream(value.split(",")).map(Double::parseDouble).toArray(Double[]::new);
                            if (tempParams.length == 2) {
                                params = new Double[]{tempParams[0], tempParams[1]};
                            } else {
                                params = new Double[]{tempParams[0], 0.0};
                            }
                            System.out.println(Main.infoColor +  "INFORMATION: " + Main.resetColor +  "Parameters: "  + Arrays.toString(params));
                        } catch (NumberFormatException exc) {
                            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid parameters!");
                            System.exit(-1);
                        }
                        break;
                    default:
                        System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid arguments!");
                        System.exit(-1);
                }
            } else {
                System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid string format!");
                System.exit(-1);
            }
        } else {
            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Invalid string format!");
            System.exit(-1);
        }
    }

}
