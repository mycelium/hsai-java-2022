import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainProcessor {

    String resultfile;
    long count;
    Double[] param;
    String type;

    public void Start(String[] args) {
        if (args.length == 0) {
            System.out.println(Main.red + "No arguments found! Please pass arguments like in example!" + Main.reset);
            System.exit(-1);
        } else {
            Arrays.stream(args).forEach(this::ProcessorArguments);
            GenerationWritingProcess genwritprocessor = new GenerationWritingProcess();
            genwritprocessor.DoGenerationWriting(resultfile, count, param, type);
        }
    }

    private void ProcessorArguments(String arg) {
        if (arg.contains("=")) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                String value = split[1].replaceAll("\s", "").toLowerCase();
                switch (split[0].replaceAll("\s", "").toLowerCase()) {
                    case "type":
                        if (value.equals ("normal")
                                || value.equals("uniform")
                                || value.equals("poisson")) {
                            type = value;
                            System.out.println("The distribution: " + Main.purple + type + Main.reset);
                        } else {
                            System.out.println(Main.red + "The distribution type is unknown! Please write distribution type like in example!" + Main.reset);
                            System.exit(-1);
                        }
                        break;
                    case "count":
                        try {
                            count = Long.parseLong(value);
                            if (count > 10000) {
                                System.out.println("The count: " + Main.purple + count + Main.reset);
                            } else {
                                System.out.println(Main.red + "The count is less than 10000! Please write count like in example!");
                                System.exit(-1);
                            }
                        }
                        catch (NumberFormatException NFE) {
                            System.out.println(Main.red + "The count is invalid! Please write count like in example!");
                            System.exit(-1);
                        }
                        break;
                    case "path":
                        Path path = Paths.get(value);
                        File file = path.toFile();
                        if (!file.exists()) {
                            System.out.println(Main.red + "The path is invalid! Please write path like in example!" + Main.reset);
                            System.exit(-1);
                        }
                        resultfile = path.toString();
                        System.out.println("The path: " + Main.purple + resultfile + Main.reset);
                        break;
                    case "parameter":
                        try {
                            Double[] tempparams = Arrays.stream(value.replaceAll("[\\[\\]\s]", "").split(",")).map(Double::parseDouble).toArray(Double[]::new);
                            if (tempparams.length == 2) {
                                param = new Double[]{tempparams[0], tempparams[1]};
                            } else {
                                param = new Double[]{tempparams[0], 0.0};
                            }
                            System.out.println("The parameters: " + Main.purple + Arrays.toString(param) + Main.reset);
                        }
                        catch (NumberFormatException NFE) {
                            System.out.println(Main.red + "The parameters are invalid! Please write parameters like in example!" + Main.reset);
                            System.exit(-1);
                        }
                        break;
                    default:
                        System.out.println(Main.red + "The arguments are invalid! Please write arguments like in example!" + Main.reset);
                        System.exit(-1);
                }
            } else {
                System.out.println(Main.red + "The string format is invalid! Please pass arguments like in example!" + Main.reset);
                System.exit(-1);
            }
        } else {
            System.out.println(Main.red + "The string format is invalid! Please pass arguments like in example!" + Main.reset);
            System.exit(-1);
        }
    }

}
