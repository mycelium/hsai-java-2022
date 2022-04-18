import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static int type;
    private static long count;
    private static Float[] parameters;
    private static String resultPath;


    /*Input order: type, count, parameters, result path
     * 1: Normal, 2: Uniform, 3: Poisson
     * Example: 1 10000 0.2,0.11 "D:/user/"*/
    public static void main(String[] args) {
        argsReader(args);
        showInfo();
        System.out.println("\nStarting to build data, and writing it to a file...\n");
    }

    private static void argsReader(String[] args) {
        try {
            type = Integer.parseInt(args[0]);
            count = Math.max(Long.parseLong(args[1]), 10000L);
            parameters = Arrays.stream(args[2].replaceAll(" ", "").split(","))
                    .map(Float::parseFloat).toArray(Float[]::new);
            resultPath = args.length > 3 ? args[3] : "";
        } catch (RuntimeException e) {
            System.out.println("Invalid arguments!");
            System.exit(-1);
        }
    }

    private static void showInfo() {
        final Object[] infoToShow = new Object[] {
                null,
                String.valueOf(count),
                String.valueOf(parameters[0]),
                parameters.length > 1 && type != 3 ? String.valueOf(parameters[1]) : ""};
        switch (type) {
            case 2 -> infoToShow[0] = new String[] {"Uniform", "Min", "Max"};
            case 3 -> infoToShow[0] = new String[] {"Poisson", "Mean", ""};
            default -> infoToShow[0] = new String[] {"Normal", "Mean", "St.dev"};
        }
        System.out.printf("Count: %s\nType: %s\n%s %s\n%s %s",
                infoToShow[1], ((String[]) infoToShow[0])[0], ((String[]) infoToShow[0])[1], infoToShow[2],
                ((String[]) infoToShow[0])[2], infoToShow[3]);
    }

    private static String getRandomNumbers() {
        String numbers = "";
        Random rnd = new Random();
        switch (type) {
            case 2:
                for (int i = 0; i < count; i++) {
                    numbers += parameters[0] + rnd.nextDouble(parameters[1] - parameters[0] + 1) + "\n";
                }
                break;
            case 3:
                double lim = Math.exp(-parameters[0]);
                int res;
                for (int i = 0; i < count; i++) {
                    double prod = rnd.nextDouble();
                    for (res = 0; prod >= lim; res++) {
                        prod *= rnd.nextDouble();
                    }
                    numbers += res + "\n";
                }
                break;
            default:
                for (int i = 0; i < count; i++) {
                    numbers += rnd.nextGaussian() * parameters[1] + parameters[0] + "\n";
                }
                break;
        }
        return numbers;
    }
}
