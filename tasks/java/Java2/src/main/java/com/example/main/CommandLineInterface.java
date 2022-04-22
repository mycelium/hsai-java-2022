package com.example.main;

import com.example.gen.Distribution;
import com.example.gen.NormalDistribution;
import com.example.gen.PoissonDistribution;
import com.example.gen.UniformDistribution;
import com.example.out.CSVWriter;
import com.example.out.Output;
import com.example.out.SQLiteWriter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.logging.Logger;

@Command(name = "Distribution", mixinStandardHelpOptions = true,
        description = "Insert generated data into CSV or SQLite")
class CommandLineInterface implements Runnable {
    private static Logger logger = Logger.getLogger(CommandLineInterface.class.getName());

    private enum DistributionType {normal, poisson, uniform}

    private enum OutputType {csv, sqlite}

    @Option(names = {"-p", "--path"},
            description = "Path for output folder",
            required = true)
    private String pathOption;

    @Option(names = {"-o", "--output"},
            description = "Output: csv or sqlite",
            required = true)
    private OutputType outputOption;
    @Option(names = {"-d", "--distribution"},
            description = "Distribution: normal, poisson or uniform",
            required = true)
    private DistributionType distributions;

    @Option(names = {"-n", "--number"},
            description = "Generation data count, must be >=10000",
            required = true)
    private Integer dataCount;


    @Option(names = {"-h", "--help"},
            description = "Help documentation",
            usageHelp = true)
    private boolean helpOption;

    @Parameters(index = "0..*",
            paramLabel = "params",
            description = "Parameters for distribution")
    private double[] params;

    @Override
    public void run() {

        Output outputWriter = null;
        switch (outputOption) {
            case sqlite -> outputWriter = new SQLiteWriter(pathOption);
            case csv -> outputWriter = new CSVWriter(pathOption);
        }

        Distribution distribution = null;
        switch (distributions) {
            case normal -> {
                if (params.length == 2) {
                    distribution = new NormalDistribution(params[0], params[1]);
                } else {
                    logger.severe("Wrong parameters for normal distribution");
                }
            }
            case poisson -> {
                if (params.length == 1) {
                    distribution = new PoissonDistribution(params[0]);
                } else {
                    logger.severe("Wrong parameters for poisson distribution");
                }
            }
            case uniform -> {
                if (params.length == 2) {
                    distribution = new UniformDistribution(params[0], params[1]);
                } else {
                    logger.severe("Wrong parameters for uniform distribution");
                }
            }
        }
        if (dataCount >= 10000) {
            outputWriter.write(distribution.getListOfPoints(dataCount));
        } else {
            logger.severe("-n must be >=10000");
        }
    }

}
