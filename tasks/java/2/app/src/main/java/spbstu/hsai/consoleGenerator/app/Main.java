package spbstu.hsai.consoleGenerator.app;

import spbstu.hsai.consoleGenerator.generator.*;
import spbstu.hsai.consoleGenerator.io.input.*;
import spbstu.hsai.consoleGenerator.io.output.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;
import java.util.logging.Logger;

public class Main {

    private static final int MIN_NUMBER = 10000;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static File getOrCreateDirectory(String dirPath) throws IllegalArgumentException {
        File outDir = new File(dirPath);
        if (!outDir.exists()) {
            if (!outDir.mkdirs()) {
                throw new IllegalArgumentException("Failed to create directory");
            }
        } else if (outDir.isFile()) {
            throw new IllegalArgumentException(outDir + " is file");
        }
        return outDir;
    }

    private static Generator getGeneratorByType(AppParams params) throws IllegalArgumentException {
        switch (params.getType()) {
            case UNIFORM: {
                Optional<Double> a = params.getMinimum();
                Optional<Double> b = params.getMaximum();
                if (!a.isPresent() || !b.isPresent()) {
                    throw new IllegalArgumentException("Invalid parameters for uniform distribution");
                }
                return UniformGenerator.getGenerator(a.get(), b.get());
            }
            case NORMAL: {
                Optional<Double> mean = params.getMean();
                Optional<Double> stddev = params.getStddev();
                if (!mean.isPresent() || !stddev.isPresent()) {
                    throw new IllegalArgumentException("Invalid parameters for normal distribution");
                }
                return NormalGenerator.getGenerator(mean.get(), stddev.get());
            }
            case POISSON: {
                Optional<Double> mean = params.getMean();
                if (!mean.isPresent()) {
                    throw new IllegalArgumentException("Invalid parameters for poisson distribution");
                }
                return PoissonGenerator.getGenerator(mean.get());
            }
            default: {
                throw new IllegalArgumentException("Invalid distribution");
            }
        }
    }

    private static Output getWriter(AppParams params) throws Exception {
        File outputDir = getOrCreateDirectory(params.getOutputDir());
        switch (params.getFormat()) {
            case CSV: {
                Path outputFilePath = Paths.get(outputDir.getPath(), "data.csv");
                logger.info("Generating random values to " + outputFilePath.toAbsolutePath());

                //return new CsvOutput(new PrintWriter(new FileOutputStream(outputFilePath.toFile())));
                return new CsvOutput(outputFilePath);

            }
            case DATABASE: {
                Path outputFilePath = Paths.get(outputDir.getPath(), "data.db");
                logger.info("Generating random values to " + outputFilePath.toAbsolutePath());
                //return new DatabaseOutput(outputFilePath.toString());
                return new DatabaseOutput(outputFilePath);
            }
            default: {
                throw new Exception("Unable to create directory");
            }
        }
    }

    public static void main(String[] args) {

        AppParams params;
        Generator dataGenerator;
        Output dataWriter;

        try {
            params = new InputReader().getParams(args);
            dataGenerator = getGeneratorByType(params);
            dataWriter = getWriter(params);
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Program ended with an error");
            return;
        }

        try {
            logger.info("Start generating");
            int n = Math.max(params.getNumber(), MIN_NUMBER);
            for (int i = 0; i < n; ++i) {
                dataWriter.write(dataGenerator.getValue());
            }
            logger.info("Generating finished");
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Program ended with an error");
            return;
        }

        logger.info("Generating completed successfully");
        System.out.println("\nFile with results: " + dataWriter.getOutputFile().toString());
        dataWriter.close();
    }
}
