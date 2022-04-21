import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final HashMap<String, String> prefs = new HashMap<>(Map.of(
            "dist", "1", "count", "10000", "par1", "", "par2", "", "dest", ""));

    //Args: type count params destination
    //Example: 2 13000 0.2,10 data/
    public static void main(String[] args) {
        if (parseInput(args) == -1) {
            return;
        }
        String path = getFileDest(getRandomData());
        if ("".equals(path)) {
            return;
        }
        displayInfo(path);
    }

    private static byte parseInput(String[] args) {
        try {
            prefs.replace("dist", args[0]);
            if (Integer.parseInt(args[1]) >= 10000) {
                prefs.replace("count", args[1]);
            }
            prefs.replace("par1", args[2].split(",")[0]);
            prefs.replace("par2", args[2].split(",").length > 1 ? args[2].split(",")[1] : "");
            if (args.length > 3) {
                prefs.replace("dest", args[3]);
            }
        } catch (RuntimeException e) {
            System.out.println("Invalid arguments!");
            return -1;
        }
        return 0;
    }

    private static String getRandomData() {
        String result = "";
        Random rnd = new Random();
        try {
            switch (prefs.get("dist"))  {
                case "2":
                    for (int i = 0; i < Integer.parseInt(prefs.get("count")); i++) {
                        result += Float.parseFloat(prefs.get("par1")) + rnd.nextDouble(
                                Float.parseFloat(prefs.get("par2"))
                                        - Float.parseFloat(prefs.get("par1")) + 1) + "\n";
                    }
                    break;
                case "3":
                    double lim = Math.exp(-Float.parseFloat(prefs.get("par1")));
                    int num;
                    for (int i = 0; i < Integer.parseInt(prefs.get("count")); i++) {
                        double prod = rnd.nextDouble();
                        for (num = 0; prod >= lim; num++) {
                            prod *= rnd.nextDouble();
                        }
                        result += num + "\n";
                    }
                    break;
                default:
                    for (int i = 0; i < Integer.parseInt(prefs.get("count")); i++) {
                        result += rnd.nextGaussian() * Float.parseFloat(prefs.get("par2"))
                                + Float.parseFloat(prefs.get("par1")) + "\n";
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid arguments!");
            return "";
        }
        return result;
    }

    private static String getFileDest(String data) {
        if ("".equals(data)) {
            return "";
        }
        File output = new File(prefs.get("dest") + "results.csv");
        try {
            output.createNewFile();
            FileWriter fileWriter = new FileWriter(output);
            fileWriter.append(data);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something get wrong with resulting file!");
            return "";
        }
        return output.getAbsolutePath();
    }

    private static void displayInfo(String path) {
        System.out.println("Getting data for the following parameters:\n");
        System.out.printf("Count: %s\n", prefs.get("count"));
        switch (prefs.get("dist")) {
            case "2" -> System.out.printf("Type: Uniform\nMin: %s\nMax: %s", prefs.get("par1"), prefs.get("par2"));
            case "3" -> System.out.printf("Type: Poisson\nMean: %s", prefs.get("par1"));
            default -> System.out.printf("Type: Normal\nMean: %s\nSt.dev: %s", prefs.get("par1"), prefs.get("par2"));
        }
        System.out.printf("\n\nOutput file: %s", path);
    }
}
