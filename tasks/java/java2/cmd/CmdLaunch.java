package cmd;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import distributions.*;
import output.CSVOutput;

public class CmdLaunch {
    private static Logger logger = Logger.getLogger("Logging");

    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            logger.info("No arguments found");
            System.exit(-1);
        }

        System.out.println("N - Normal, U - Uniform, P - Poisson: " + args[0]);
        String type = args[0];

        System.out.println("Number of values: " + args[1]);
        int numberOfValues = Integer.parseInt(args[1]);

        System.out.println("Parameters (example: 1,2): " + args[2]);
        List<Double> params = Arrays.stream(args[2].split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        System.out.println("Path to save output: " + args[3]);
        String dir = args[3];

        List<Double> numbersFromDistribution = null;
        switch (type) {
            case ("N"):
                NormalDistribution n = new NormalDistribution(numberOfValues, params.get(0), params.get(1));
                numbersFromDistribution = n.generateNumbers(numberOfValues);
                break;
            case ("U"):
                UniformDistribution u = new UniformDistribution(numberOfValues, params.get(0), params.get(1));
                numbersFromDistribution = u.generateNumbers(numberOfValues);
                break;
            case ("P"):
                PoissonDistribution p = new PoissonDistribution(numberOfValues, params.get(0));
                numbersFromDistribution = p.generateNumbers(numberOfValues);
                break;
            default:
                logger.info("Typo in distribution type");
                break;
        }

        CSVOutput csv = new CSVOutput();
        String csvPath = dir + "result.csv";
        logger.info("csv file created " + csvPath);
        csv.writeToCsv(csvPath, numbersFromDistribution);
    }
}