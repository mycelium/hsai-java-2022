package input;

import enums.DistributionType;
import enums.WriterType;
import org.apache.commons.cli.*;
import data.AppParams;

import java.nio.file.InvalidPathException;

public class ArgsReader implements InputReader {

    private static final String OPTION_DISTRIBUTION = "distribution";
    private static final String OPTION_ORIGIN = "origin";
    private static final String OPTION_BOUND = "bound";
    private static final String OPTION_MEAN = "mean";
    private static final String OPTION_STDDEV = "stddev";
    private static final String OPTION_NUMBER = "number";
    private static final String OPTION_FORMAT = "format";
    private static final String OPTION_TARGET = "target";

    private static final String DISTRIBUTION_VALUE_UNIFORM = "uniform";
    private static final String DISTRIBUTION_VALUE_NORMAL = "normal";
    private static final String DISTRIBUTION_VALUE_POISSON = "poisson";

    private static final String FORMAT_CSV = "csv";
    private static final String FORMAT_DATABASE = "database";

    private final String[] args;

    public ArgsReader(String[] args) {
        this.args = args;
    }

    /**
     * @throws NumberFormatException    - if distribution params are not numbers
     * @throws InvalidPathException     – if the path string cannot be converted to a Path
     * @throws IllegalArgumentException - if args cannot be parsed
     */
    @Override
    public AppParams readAppParams() throws IllegalArgumentException {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            throw new IllegalArgumentException(e);
        }

        String distributionStr = cmd.getOptionValue(OPTION_DISTRIBUTION);
        String writerType = cmd.getOptionValue(OPTION_FORMAT);

        AppParams params = new AppParams()
                .setDistributionType(switch (distributionStr) {
                    case DISTRIBUTION_VALUE_UNIFORM -> DistributionType.UNIFORM;
                    case DISTRIBUTION_VALUE_NORMAL -> DistributionType.NORMAL;
                    case DISTRIBUTION_VALUE_POISSON -> DistributionType.POISSON;
                    default -> throw new IllegalArgumentException("unknown distribution " + distributionStr);
                })
                .setNumber(Integer.parseInt(cmd.getOptionValue(OPTION_NUMBER)))
                .setTargetDirectory(cmd.getOptionValue(OPTION_TARGET))
                .setWriterType(switch (writerType) {
                    case FORMAT_CSV -> WriterType.CSV;
                    case FORMAT_DATABASE -> WriterType.DATABASE;
                    default -> throw new IllegalArgumentException("unknown format " + writerType);
                });
        if (cmd.hasOption(OPTION_ORIGIN)) {
            params.setOrigin(Double.parseDouble(cmd.getOptionValue(OPTION_ORIGIN)));
        }
        if (cmd.hasOption(OPTION_BOUND)) {
            params.setBound(Double.parseDouble(cmd.getOptionValue(OPTION_BOUND)));
        }
        if (cmd.hasOption(OPTION_MEAN)) {
            params.setMean(Double.parseDouble(cmd.getOptionValue(OPTION_MEAN)));
        }
        if (cmd.hasOption(OPTION_STDDEV)) {
            params.setStddev(Double.parseDouble(cmd.getOptionValue(OPTION_STDDEV)));
        }

        return params;
    }

    private Options getOptions() {
        Options options = new Options();

        Option distributionType = new Option("d", OPTION_DISTRIBUTION, true, "distribution type (uniform/normal/poisson)");
        distributionType.setRequired(true);
        options.addOption(distributionType);

        options.addOption(new Option("a", OPTION_ORIGIN, true, "lower bound, inclusive)"));
        options.addOption(new Option("b", OPTION_BOUND, true, "upper bound, exclusive)"));
        options.addOption(new Option("m", OPTION_MEAN, true, "mean"));
        options.addOption(new Option("s", OPTION_STDDEV, true, "standard deviation"));

        Option nValues = new Option("n", OPTION_NUMBER, true, "number of values");
        nValues.setRequired(true);
        options.addOption(nValues);

        Option outputFormat = new Option("f", OPTION_FORMAT, true, "output format: csv/database)");
        outputFormat.setRequired(true);
        options.addOption(outputFormat);

        Option targetDir = new Option("t", OPTION_TARGET, true, "target directory");
        targetDir.setRequired(true);
        options.addOption(targetDir);

        return options;
    }
}

