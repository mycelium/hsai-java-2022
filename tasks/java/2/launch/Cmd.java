package launch;

import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;
import java.util.logging.Logger;

import distributions.*;
import output.CSV;

public class Cmd {
    private static Logger logs = Logger.getLogger("Logs");

    public static void main(String args[]) throws Exception {

        // Write logs to file
        FileHandler fh = new FileHandler("distribution.log");
        logs.addHandler(fh);

        // Fill arguments
        if (args.length == 0) {
            System.out.println("No arguments found");
            System.exit(-1);
        }

        System.out.println("Type (normal, uniform, poisson): " + args[0]); // normal, uniform, poisson
        String type = args[0];

        System.out.println("Size: " + args[1]);
        int size = Integer.parseInt(args[1]);

        System.out.println("Parameters: " + args[2]);
        List<Double> params = Arrays.stream(args[2].split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        System.out.println("Directory: " + args[3]); // dir\where\to\save\result\
        String dir = args[3];

        List<Double> num_list = null;
        System.out.println("Distribution's generation begins");
        logs.info("Distribution's generation begins");

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

        System.out.println("Distribution's generation is done");
        logs.info("Distribution's generation is done");

        // Write results to file
        CSV w = new CSV();
        String path = dir + "distribution.csv";
        w.write(path, num_list);
        System.out.println("Writing is done");
        System.out.println("Result saved in " + path);

        logs.info("Distribution has been written to " + path);
    }
}
