import java.io.File;
import java.util.Arrays;
import java.util.Locale;

public class ArgsParser {
    public static void parseArgs(String[] args) {
        for (String par : args) {
            par.replaceAll("\s", "");
            String whichParam = par.substring(0, par.indexOf('=')).toLowerCase();
            String paramValue = par.substring(par.indexOf('=') + 1).toLowerCase();
            switch (whichParam) {
                case "dist":
                    switch (paramValue) {
                        case "u" -> Main.type = 'u';
                        case "n" -> Main.type = 'n';
                        case "p" -> Main.type = 'p';
                        default -> System.out.println("Wrong dist");
                    }
                    break;
                case "params":
                    try {
                        var pars = Arrays.stream(paramValue.split(",")).map(Float::parseFloat).toArray(Float[]::new);
                        Main.params = pars;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "cnt":
                    int val = Integer.parseInt(paramValue);
                    if (val > 10000) Main.cnt = val;
                    else System.out.println("Wrong cnt");
                    break;
                case "out":
                    String path = paramValue.replaceAll("\\\\", "/");
                    File file = new File(path);
                    if (file.exists()) Main.out = path;
                    else System.out.println("Wrong out");
                    break;
            }
        }
    }
}
