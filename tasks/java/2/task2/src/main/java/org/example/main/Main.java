package org.example.main;

import org.example.distribution.Distribution;
import org.example.distribution.NormalDistribution;
import org.example.distribution.PoissonDistribution;
import org.example.distribution.UniformDistribution;
import org.example.io.CSVOutput;
import org.example.io.Output;
import org.example.io.SqliteOutput;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class Main {

    public static void main(String[] args) {
        //args = getArgs();
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    private static String[] getArgs() {
        //String[] result = "10 15 -n 15 -o csv -f test.csv -d normal".split(" ");
        String[] result = "10 20 -n 3 -o sqlite -f test.db -d uniform".split(" ");
        //String[] result = "--help".split(" ");
        return result;
    }
}

@Command(name = "Generator-data", mixinStandardHelpOptions = true,
    description = "This app can generate data, log and save to sqlite or csv")
class App implements Runnable {

    private enum DistributionType { normal, poisson, uniform }
    private enum OutputType { csv, sqlite }

    @Parameters(paramLabel = "params", description = "parameters for distribution")
    private double[] params;

    @Option(names = {"-n"}, description = "data size", required = true)
    private Integer n;

    @Option(names = {"-o", "--output"}, description = "type for output (csv or sqlite)", required = true)
    private OutputType outputType;

    @Option(names = {"-f", "--file"}, description = "file/directory to output data", required = true)
    private String path;

    @Option(names = {"-d", "--distribution"}, description = "type for distribution (normal, poisson or uniform)", required = true)
    private DistributionType distributionType;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;

    @Override
    public void run() {
        Output output = null;
        switch (outputType) {
            case csv -> output = new CSVOutput(path);
            case sqlite -> output = new SqliteOutput(path);
        }

        Distribution distribution = null;
        switch (distributionType) {
            case normal -> {
                if (params.length != 2) {
                    System.err.println("Wrong params for distribution " + distributionType);
                    System.exit(0);
                }
                distribution = new NormalDistribution(params[0], params[1]);
            }
            case poisson -> {
                if (params.length != 1) {
                    System.err.println("Wrong params for distribution " + distributionType);
                    System.exit(0);
                }
                distribution = new PoissonDistribution(params[0]);
            }
            case uniform -> {
                if (params.length != 2) {
                    System.err.println("Wrong params for distribution " + distributionType);
                    System.exit(0);
                }
                distribution = new UniformDistribution(params[0], params[1]);
            }
        }

        if (n <= 0) {
            System.err.println("Parameter n must be greater than 0!");
            System.exit(0);
        }
        output.save(distribution.getPointList(n));
    }
}