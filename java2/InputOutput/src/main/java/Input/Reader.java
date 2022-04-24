package Input;

import Distribution.Distribution;
import Distribution.NormalDistribution;
import Distribution.PoissonDistribution;
import Distribution.UniformDistribution;
import Output.CSVWriter;
import Output.DBWriter;
import Output.Writer;

import java.util.logging.Logger;

public class Reader {

    private static final Logger log = Logger.getLogger(Reader.class.getName());

    public static void main(String[] args) {
        Writer writer;
        Distribution distribution;
        switch (args[0]) {                                                     //Distribution type
            case "uniform" -> {
                if (args[1].matches("-?\\d+(\\.\\d+)?")                  //Distribution parameters
                        && args[2].matches("-?\\d+(\\.\\d+)?")           //Distribution parameters
                        && args[3].matches("\\d+")) {                    //Quantity
                    distribution = new UniformDistribution(Double.parseDouble(args[1])
                            , Double.parseDouble(args[2]));
                    writer = checkType(args[4], distribution);
                    writer.write(distribution.generateData(Integer.parseInt(args[3])));
                } else {
                    log.severe("parameters incorrect");
                    System.exit(0);
                }
            }
            case "poisson" -> {
                if (args[1].matches("-?\\d+(\\.\\d+)?") && args[2].matches("\\d+")) {
                    distribution = new PoissonDistribution(Double.parseDouble(args[1]));
                    writer = checkType(args[3], distribution);
                    writer.write(distribution.generateData(Integer.parseInt(args[2])));
                } else {
                    log.severe("parameters incorrect");
                    System.exit(0);
                }
            }
            case "normal" -> {
                if (args[1].matches("-?\\d+(\\.\\d+)?")
                        && args[2].matches("-?\\d+(\\.\\d+)?")
                        && args[3].matches("\\d+")) {
                    distribution = new NormalDistribution(Double.parseDouble(args[1])
                            , Double.parseDouble(args[2]));
                    writer = checkType(args[4], distribution);
                    writer.write(distribution.generateData(Integer.parseInt(args[3])));
                } else {
                    log.severe("parameters incorrect");
                    System.exit(0);
                }
            }
            default -> {
                log.severe("Distribution type incorrect");
                System.exit(0);
            }
        }
    }

    private static Writer checkType(String filePath, Distribution distribution) {

        switch (filePath.substring(filePath.lastIndexOf("."))) {
            case ".csv" -> {
                return new CSVWriter(filePath);
            }
            case ".db" -> {
                return new DBWriter(filePath);
            }
            default -> {
                log.severe("File type or path incorrect");
                System.exit(0);
                return null;
            }
        }
    }
}
