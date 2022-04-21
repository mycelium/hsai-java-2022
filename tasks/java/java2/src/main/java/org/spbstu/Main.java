package org.spbstu;

import java.util.logging.Logger;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

public class Main {
    public static void main(String[] args) {
        System.exit(new CommandLine(new GeneratorValues()).execute(args));
    }
}

@Command(name = "Generator values", mixinStandardHelpOptions = true)
class GeneratorValues implements Runnable {

    private static final Logger log = Logger.getLogger(GeneratorValues.class.getName());

    private enum DistributionType { normal, poisson, uniform }
    private enum WriterType { csv, sqlite }

    @Parameters(paramLabel = "PARAMETERS", description = "Parameters for distribution")
    private double[] parameters;

    @Option(names = "-n", description = "Amount of values to generate", required = true)
    private Integer n;

    @Option(names = "-w", description = "Writer type (csv or sqlite)", required = true)
    private WriterType writerType;

    @Option(names = "-f", description = "File to write generated values", required = true)
    private String stringPath;

    @Option(names = "-d", description = "Distribution type (normal, poisson or uniform)", required = true)
    private DistributionType distributionType;

    @Override
    public void run() {
        checkInput();
        Writer writer = getWriter();
        Distribution distribution = getDistribution();
        writer.write(distribution.until(n));
    }

    private Distribution getDistribution() {
        return switch (distributionType) {
            case normal -> new NormalDistribution(parameters[0], parameters[1]);
            case poisson -> new PoissonDistribution(parameters[0]);
            case uniform -> new UniformDistribution(parameters[0], parameters[1]);
        };
    }

    private Writer getWriter() {
        return switch (writerType) {
            case csv -> new CSVWriter(stringPath);
            case sqlite -> new SQLiteWriter(stringPath);
        };
    }

    private void checkInput() {
        String fileExtension = stringPath.substring(stringPath.lastIndexOf('.') + 1);

        switch (writerType) {
            case csv -> { if (!fileExtension.equals("csv")) exitWithMessage("Wrong extension for CSV file!"); }
            case sqlite -> { if (!fileExtension.equals("db")) exitWithMessage("Wrong extension for DB file!"); }
            case null -> exitWithMessage("Wrong type for writer");
        }

        switch (distributionType) {
            case normal -> { if (parameters.length != 2) exitWithMessage("For normal distribution need 2 parameters!"); }
            case poisson -> { if (parameters.length != 1) exitWithMessage("For poisson distribution need 1 parameter!"); }
            case uniform -> { if (parameters.length != 2) exitWithMessage("For uniform distribution need 2 parameters!"); }
            case null -> exitWithMessage("Wrong type for distribution!");
        }
    }

    private void exitWithMessage(String msg) {
        log.severe(msg);
        System.exit(0);
    }

}
