package spbstu.hsai.consoleGenerator.io.input;

import org.apache.commons.cli.*;

public class InputReader {

    private Options getOptions() {

        Options options = new Options();

        options.addRequiredOption("t", "type", true, "distribution type (uniform/normal/poisson)");
        options.addRequiredOption("n", "number", true, "number of values");
        options.addRequiredOption("o", "out", true, "output directory");
        options.addRequiredOption("f", "format", true, "output format (csv/database)");

        options.addOption(new Option("a", true, "minimum value"));
        options.addOption(new Option("b", true, "maximum value"));
        options.addOption(new Option("m", "mean", true, "mean"));
        options.addOption(new Option("s", "stddev", true, "standard deviation"));

        return options;
    }

    public AppParams getParams(String[] args) throws IllegalArgumentException {

        Options options = getOptions();
        CommandLine cmdLine;
        CommandLineParser cmdLineParser = new DefaultParser();
        HelpFormatter help = new HelpFormatter();
        try {
            cmdLine = cmdLineParser.parse(options, args);
        } catch (ParseException e) {
            help.printHelp("generator", options);
            throw new IllegalArgumentException(e);
        }

        AppParams params = new AppParams();

        String typeValue = cmdLine.getOptionValue("type");
        String formatValue = cmdLine.getOptionValue("format");

        DistributionType type;
        OutputFormat format;

        switch (typeValue) {
            case "uniform": {
                type = DistributionType.UNIFORM;
                break;
            }
            case "normal": {
                type = DistributionType.NORMAL;
                break;
            }
            case "poisson": {
                type = DistributionType.POISSON;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid distribution: " + typeValue);
            }
        }

        switch (formatValue) {
            case "csv": {
                format = OutputFormat.CSV;
                break;
            }
            case "database": {
                format = OutputFormat.DATABASE;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid format: " + formatValue);
            }
        }

        params.setType(type);
        params.setFormat(format);
        params.setNumber(Integer.parseInt(cmdLine.getOptionValue("n")));
        params.setOutputDir(cmdLine.getOptionValue("o"));

        if (cmdLine.hasOption("a")) {
            params.setMinimum(Double.parseDouble(cmdLine.getOptionValue("a")));
        }
        if (cmdLine.hasOption("b")) {
            params.setMaximum(Double.parseDouble(cmdLine.getOptionValue("b")));
        }
        if (cmdLine.hasOption("mean")) {
            params.setMean(Double.parseDouble(cmdLine.getOptionValue("mean")));
        }
        if (cmdLine.hasOption("stddev")) {
            params.setStddev(Double.parseDouble(cmdLine.getOptionValue("stddev")));
        }

        return params;
    }
}
