import java.io.File;
import java.util.Arrays;

public class CLI {

    public static void readArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Arguments required!");
            System.exit(-1);
        }
        Arrays.stream(args).forEach(arg -> {
            String argVal = arg.substring(arg.indexOf('=') + 1).replaceAll("\s", "")
                    .toLowerCase();
            switch(arg.substring(0, arg.indexOf('=')).replaceAll("\s","").toLowerCase()) {
                case "type":
                    switch (argVal) {
                        case "norm" -> Main.params.replace("type", Distribution.NORMAL); //Dunno how to do here empty case to get rid of redundancy
                        case "uni" -> Main.params.replace("type", Distribution.UNIFORM);
                        case "pois" -> Main.params.replace("type", Distribution.POISSON);
                        default -> System.out.println("Unknown distribution type! Normal distribution will be used.");
                    }
                    break;
                case "cnt":
                    try {
                        long tmp = Long.parseLong(argVal);
                        if (tmp > 10000L) { Main.params.replace("cnt", tmp); }
                    } catch (NumberFormatException NFE) {
                        System.out.println("Invalid cnt number! Default (10000) will be used.");
                    }
                    break;
                case "out":
                    String path = argVal.replaceFirst("^[\\\\/]", "");
                    if (new File(path).exists()) { Main.params.replace("out", path); }
                    else { System.out.println("Cannot find a path specified! Writing to a default location."); }
                    break;
                case "params":
                    try {
                        Float[] tmp = Arrays.stream(argVal.replaceAll("[\\[\\]\s]", "")
                                .split(",")).map(Float::parseFloat).toArray(Float[]::new);
                        if (((Distribution) Main.params.get("type")).getParameterNumber() == 2) {
                            try {
                                Main.params.replace("params", new Float[] {tmp[0], tmp[1]});
                            } catch (IndexOutOfBoundsException IOB) {
                                Main.params.replace("params", new Float[] {tmp[0], 0f});
                            }
                        } else { Main.params.replace("params", new Float[] {tmp[0], 0f}); }
                    } catch (NumberFormatException NFE) {
                        System.out.println("Invalid parameters for distribution!");
                        System.exit(-1);
                    }
                    break;
                default:
                    System.out.println("Invalid arguments!");
                    System.exit(-1);
            }
        });
    }

    public static void showResponse(File result) {
        System.out.println("Resulting file: " + result.getAbsolutePath());
    }
}
