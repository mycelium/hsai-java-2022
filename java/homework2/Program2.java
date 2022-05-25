package homework2;

import homework2.IO.CSV;
import homework2.IO.DB;
import homework2.distributions.Normal;
import homework2.distributions.Poisson;
import homework2.distributions.Uniform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Program2 {
    public static void main(String[] args) throws IOException {
        /*
        args[0] - distr type
        args[1 (, 2)] - params
        args[3 (2 for pois)] - number of values
        args[4 (3 for pois)] - format
        args[5 (4 for pois)] - output directory
         */
        Logger log = Logger.getLogger(Program2.class.getName());
        ArrayList<Double> data = null;
        String format = null, outputDir = null;
        switch (args[0]) {
            case "uniform" -> {
                Uniform uni = new Uniform(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                data = uni.generate(Integer.parseInt(args[3]));
                format = args[4];
                outputDir = args[5];
            }
            case "normal" -> {
                Normal norm = new Normal(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                data = norm.generate(Integer.parseInt(args[3]));
                format = args[4];
                outputDir = args[5];
            }
            case "poisson" -> {
                Poisson pois = new Poisson(Double.parseDouble(args[1]));
                data = pois.generate(Integer.parseInt(args[2]));
                format = args[3];
                outputDir = args[4];
            }
            default -> {
                log.severe("Unrecognised distribution type, quitting...");
                System.exit(-1);
            }
        }

        switch (format) {
            case "CSV" -> {
                CSV csv = new CSV(new File(outputDir));
                csv.write(data);
            }
            case "DB" -> {
                DB db = new DB(new File(outputDir));
                db.write(data);
            }
            default -> {
                log.severe("Unrecognised output format, quitting...");
                System.exit(-1);
            }
        }

    }

}
