package main;

import distribution.ContinuousUniformDistribution;
import distribution.Distribution;
import distribution.NormalDistribution;
import distribution.PoissonDistribution;
import enums.WriterType;
import input.ArgsReader;
import output.CsvWriter;
import output.DatabaseWriter;
import output.OutputWriter;
import data.AppParams;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Logger;

public class Main {

    private static final String FILENAME_RESULT_CSV = "result.csv";
    private static final String FILENAME_RESULT_DATABASE = "result.db";

    private static final Logger logger = Logger.getLogger("app");

    private static Distribution<?> getDistributionByParams(AppParams params) throws IllegalArgumentException {
        switch (params.getDistributionType()) {
            case UNIFORM -> {
                if (params.getOrigin().isEmpty() || params.getBound().isEmpty()) {
                    throw new IllegalArgumentException("specify origin and bound for uniform distribution");
                }
                double origin = params.getOrigin().get();
                double bound = params.getBound().get();
                return ContinuousUniformDistribution.create(new Random(), origin, bound);
            }
            case NORMAL -> {
                if (params.getMean().isEmpty() || params.getStddev().isEmpty()) {
                    throw new IllegalArgumentException("specify mean and stddev for normal distribution");
                }
                double mean = params.getMean().get();
                double stddev = params.getStddev().get();
                return NormalDistribution.create(new Random(), mean, stddev);
            }
            case POISSON -> {
                if (params.getMean().isEmpty()) {
                    throw new IllegalArgumentException("specify mean for poisson distribution");
                }
                return PoissonDistribution.create(new Random(), params.getMean().get());
            }
            default -> {
                throw new IllegalArgumentException("unknown distribution");
            }
        }
    }

    private static OutputWriter getWriter(WriterType writerType, String targetDirectoryPath) throws Exception {
        switch (writerType) {
            case CSV -> {
                Path targetFilePath = Paths.get(targetDirectoryPath, FILENAME_RESULT_CSV);
                logger.info("Generating random values to " + targetFilePath.toAbsolutePath());
                return new CsvWriter(new PrintWriter(new FileOutputStream(targetFilePath.toFile())));
            }
            case DATABASE -> {
                Path targetFilePath = Paths.get(targetDirectoryPath, FILENAME_RESULT_DATABASE);
                logger.info("Generating random values to " + targetFilePath.toAbsolutePath());
                return new DatabaseWriter(targetFilePath.toString());
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private static File createDirectoryIfNotExists(String directoryPath) throws IllegalArgumentException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IllegalArgumentException("failed to create directory");
            }
        } else if (directory.isFile()) {
            throw new IllegalArgumentException(directory + " is file");
        }
        return directory;
    }

    public static void main(String[] args) {
        AppParams params;
        Distribution<?> distribution;
        try {
            params = new ArgsReader(args).readAppParams();
            distribution = getDistributionByParams(params);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        File targetDirectory;
        try {
            targetDirectory = createDirectoryIfNotExists(params.getTargetDirectory());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        try (OutputWriter writer = getWriter(params.getWriterType(), targetDirectory.getPath())) {
            int logThreshold = 100;
            for (int i = 0; i < params.getNumber(); i++) {
                writer.write(distribution.get());
                if ((i + 1) % logThreshold == 0) {
                    logger.info(String.format("Generated %d out of %d values", i + 1, params.getNumber()));
                }
            }
            logger.info("Generating finished");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}