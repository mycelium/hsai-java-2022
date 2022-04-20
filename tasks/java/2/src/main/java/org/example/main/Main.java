package org.example.main;


import org.example.distribution.*;
import org.example.io.*;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        String[] split = "-h".split(" ");
        int exitCode = new CommandLine(new CLI()).execute(split);
    }
}

@Command(name = "Data-generator", mixinStandardHelpOptions = true,
        description = "Generate data and save it to csv or sqlite")
class CLI implements Runnable {
    private static Logger logger = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(DBWriter.class.getName());
    }


    private enum DistributionType {normal, poisson, uniform}

    private enum OutputType {csv, sqlite}

    @Option(names = {"-d", "--distribution"}, description = "Distribution type", required = true)
    private DistributionType distributionOption;

    @Option(names = {"-n", "--number"}, description = "Number of generated data", required = true)
    private Integer nOption;

    @Option(names = {"-p", "--path"}, description = "Output folder path", required = true)
    private String pathOption;

    @Option(names = {"-o", "--output"}, description = "Output type", required = true)
    private OutputType outputOption;

    @Option(names = {"-h", "--help"}, description = "Display help info", usageHelp = true)
    private boolean helpOption;

    @Parameters(index = "0..*", paramLabel = "params", description = "Distribution parameters")
    private double[] paramsOption;

    @Override
    public void run() {
        Distribution distribution = null;
        switch (distributionOption) {
            case normal -> {
                if (paramsOption.length == 2) {
                    distribution = new NormalDistribution(paramsOption[0], paramsOption[1]);
                } else {
                    logger.severe("Illegal number of parameters for normal distribution");
                }
            }
            case poisson -> {
                if (paramsOption.length == 1) {
                    distribution = new PoissonDistribution(paramsOption[0]);
                } else {
                    logger.severe("Illegal number of parameters for poisson distribution");
                }
            }
            case uniform -> {
                if (paramsOption.length == 2) {
                    distribution = new UniformDistribution(paramsOption[0], paramsOption[1]);
                } else {
                    logger.severe("Illegal number of parameters for uniform distribution");
                }
            }
        }

        Writer writer = null;
        switch (outputOption){
            case csv -> writer = new CSVWriter(pathOption);
            case sqlite -> writer = new DBWriter(pathOption);
        }

        if (nOption >= 10000 && writer!=null && distribution != null) {
            writer.write(distribution.getPoints(nOption));
        }
        else {
            logger.severe("Number of generating data must be greater than 10000");
        }
    }
}

