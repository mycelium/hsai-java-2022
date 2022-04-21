package com.polytech;

import com.polytech.generator.*;
import com.polytech.io.*;
import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "data-generator", mixinStandardHelpOptions = true, description = "data-generator")
public class Main implements Runnable{

    private static final Logger logger = new ConsoleLogger(Main.class.getName());

    @Parameters(paramLabel = "parameters", description = "distribution's parameters")
    private double[] parameters;

    @Option(names = "-w", description = "writer type { csv, sqlite }", required = true)
    private WriterType writerType;

    @Option(names = "-f", description = "file (.csv or .db) to write data", required = true)
    private String file;

    @Option(names = "-d", description = "distribution type { normal, poisson, uniform }", required = true)
    private DistributionType distributionType;

    @Option(names = "-n", description = "data size", required = true)
    private int number;

    public static void main(String[] args) {
        System.exit(new CommandLine(new Main()).execute(args));
    }

    @Override
    public void run() {
        logger.trace("start application");
        Writer writer = getWriter();
        Distribution distribution = getDistribution();
        writer.write(distribution.generate(number));
        logger.trace("successful end application");
    }

    private Writer getWriter() {
        return switch (writerType) {
            case csv -> new CSVWriter(file);
            case sqlite -> new SQLiteWriter(file);
        };
    }

    private Distribution getDistribution() {
        return switch (distributionType) {
            case normal -> new NormalDistribution(parameters[0], parameters[1]);
            case poisson -> new PoissonDistribution(parameters[0]);
            case uniform -> new UniformDistribution(parameters[0], parameters[1]);
        };
    }
}
