import csv.Csv;
import distributions.impl.Normal;
import distributions.impl.Poisson;
import distributions.impl.Uniform;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Application2 {
    private static Logger logs = Logger.getLogger("Logs");

    public static void main(String[] args) {

        //normal 10001 "3.1, 2.3" 2
        //normal, uniform, poisson

        try {
            FileHandler fh = new FileHandler("distribution.log");
            logs.addHandler(fh);
        } catch (IOException exception) {
            System.out.println("Ouch, IOException because: " + exception.getCause());
        }

        if (args.length < 4) {
            System.out.println("Incorrect number of arguments");
            System.exit(-1);
        }

        System.out.printf("Type (normal, uniform, poisson): %s\n", args[0]);
        String type = args[0];

        System.out.printf("Size: %s\n", args[1]);
        int size = Integer.parseInt(args[1]);

        System.out.printf("Parameters: %s\n", args[2]);
        List<Double> params = Arrays.stream(args[2].split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        System.out.printf("Directory: %s\n", args[3]);
        String dir = args[3];

        List<Double> num_list = null;
        System.out.println("Distribution's generation begins");
        logs.info("Distribution's generation begins");

        try {
            switch (type) {
                case ("normal"):
                    Normal n = new Normal(size, params.get(0), params.get(1));
                    num_list = n.genNumbers(size);
                    break;
                case ("uniform"):
                    Uniform u = new Uniform(size, params.get(0), params.get(1));
                    num_list = u.genNumbers(size);
                    break;
                case ("poisson"):
                    Poisson p = new Poisson(size, params.get(0));
                    num_list = p.genNumbers(size);
                    break;
                default:
                    System.out.println("No such distribution");
                    break;
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Ouch, smth wrong with arguments because: " + exception.getCause());
        }

        System.out.println("Distribution's generation is done");
        logs.info("Distribution's generation is done");

        String path = dir + "distribution.csv";
        Csv.write(path, num_list);
        System.out.printf("Distribution has been written to %s\n", path);

        logs.info(String.format("Distribution has been written to %s", path));

    }
}