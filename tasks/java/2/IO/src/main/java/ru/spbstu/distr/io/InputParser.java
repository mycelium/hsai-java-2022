package ru.spbstu.distr.io;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbstu.distr.core.AbstractGenerator;
import ru.spbstu.distr.core.NormalGenerator;
import ru.spbstu.distr.core.PoissonGenerator;
import ru.spbstu.distr.core.UniformGenerator;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class InputParser {
    CommandLine cmd;
    private AbstractGenerator generator;
    private long number;
    private AbstractExporter exporter;

    public InputParser(String[] args) {
        Options opts = new Options();
        opts.addRequiredOption("t", "type", true, "Distribution type");

        Option paramsOpt = new Option("p", "parameters", true, "Distribution parameters");
        paramsOpt.setArgs(Option.UNLIMITED_VALUES);
        paramsOpt.setValueSeparator(',');
        paramsOpt.setRequired(true);
        opts.addOption(paramsOpt);

        opts.addRequiredOption("n", "number", true, "Number of values");
        opts.addRequiredOption("f", "format", true, "Output format");
        opts.addRequiredOption("o", "out", true, "Output directory");

        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(opts, args);
        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Distributor", opts);
            System.exit(1);
        }

        Logger log = LogManager.getLogger(InputParser.class);

        String type = cmd.getOptionValue('t');
        String[] params = cmd.getOptionValues('p');
        String misParamsMsg = String.format("Missing %d parameter(s) for %s distribution", (2 - params.length), type);
        switch (type) {
            case "uniform":
                if (params.length >= 2) {
                    try {
                        double a = Double.parseDouble(params[0]);
                        double b = Double.parseDouble(params[1]);
                        generator = new UniformGenerator(a, b);
                        log.info("Uniform generator with parameters A={}, B={} created", a, b);
                    } catch (NumberFormatException nfe) {
                        log.error("Wrong parameter(s) format");
                    }
                } else {
                    log.error(misParamsMsg);
                }
                break;
            case "poisson":
                if (params.length >= 1) {
                    try {
                        double lambda = Double.parseDouble(params[0]);
                        generator = new PoissonGenerator(lambda);
                        log.info("Poisson generator with parameter Lambda={} created", lambda);
                    } catch (NumberFormatException nfe) {
                        log.error("Wrong parameter(s) format");
                    }
                } else {
                    log.error(misParamsMsg);
                }
                break;
            case "normal":
                if (params.length >= 2) {
                    try {
                        double mx = Double.parseDouble(params[0]);
                        double s = Double.parseDouble(params[1]);
                        generator = new NormalGenerator(mx, s);
                        log.info("Normal generator with parameters MX={}, S={} created", mx, s);
                    } catch (NumberFormatException nfe) {
                        log.error("Wrong parameter(s) format");
                    }
                } else {
                    log.error(misParamsMsg);
                }
                break;
            default:
                log.error("Invalid distribution type: {}", type);
        }

        try {
            number = Integer.parseUnsignedInt(cmd.getOptionValue('n'));
            log.info("Requested {} values", number);
        } catch (NumberFormatException nfe) {
            log.error("Wrong number of values format");
        }

        Path outDir = null;
        String specifiedPath = cmd.getOptionValue('o');
        try {
            outDir = Paths.get(specifiedPath);
        } catch (InvalidPathException ipe) {
            log.error("Invalid output path provided");
        }

        if (outDir != null) {
            Path outFile;

            String outFormat = cmd.getOptionValue('f');
            switch (outFormat) {
                case "csv":
                    outFile = outDir.resolve("sample.csv");
                    exporter = new CsvExporter(outFile);
                    log.info("CSV exporter created, working dir: {}", outDir);
                    break;
                case "sqlite":
                    outFile = outDir.resolve("sample.db");
                    exporter = new SqliteExporter(outFile);
                    log.info("SQLite exporter created, working dir: {}", outDir);
                    break;
                default:
                    log.error("Invalid output format type: {}", outFormat);
                    return;
            }

            if (Files.exists(outFile)) {
                log.error("File {} already exists", outFile);
                exporter = null;
            }
        }
    }

    public AbstractGenerator getGenerator() {
        return generator;
    }

    public long getNumber() {
        return number;
    }

    public AbstractExporter getExporter() {
        return exporter;
    }

    public boolean isValid() {
        return Stream.of(generator, number, exporter).allMatch(Objects::nonNull);
    }
}
