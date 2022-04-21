package io.parameters;

import distribution.ContinuousUniformDistributionFactory;
import distribution.IDistributionFactory;
import distribution.NormalDistributionFactory;
import distribution.PoissonDistributionFactory;
import io.output.CsvWriter;
import io.output.OutputWriter;
import io.output.SQLiteWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

public class ArgumentsParser {
    private NormalDistributionFactory parseNormalDistribution(Arguments arguments) {
        try {
            return NormalDistributionFactory.of(Double.parseDouble(arguments.get("mean")),
                    Double.parseDouble(arguments.get("stddev")));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("No stddev or mean in arguments for normal distribution");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid stddev or mean format for normal distribution");
        }
    }

    private ContinuousUniformDistributionFactory parseContinuousUniformDistribution(Arguments arguments) {
        try {
            return ContinuousUniformDistributionFactory.of(Double.parseDouble(arguments.get("origin")),
                    Double.parseDouble("bound"));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("No origin or bound in arguments for continuous distribution");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid origin or bound format for continuous distribution");
        }
    }

    private PoissonDistributionFactory parsePoisson(Arguments arguments) {
        try {
            return PoissonDistributionFactory.of(Double.parseDouble(arguments.get("lambda")));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("No lambda in arguments for poisson distribution");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid lambda format for poisson distribution");
        }
    }

    public IDistributionFactory parseDistribution(Arguments arguments) {
        var type = arguments.get("type");
        if (type == null) {
            throw new IllegalArgumentException("No specified distribution type");
        }
        return switch (type) {
            case "normal" -> parseNormalDistribution(arguments);
            case "continuous" -> parseContinuousUniformDistribution(arguments);
            case "poisson" -> parsePoisson(arguments);
            default -> throw new IllegalArgumentException("Unhandled distribution type");
        };
    }

    public OutputWriter parseOutput(Arguments arguments) throws IOException, SQLException {
        var type = arguments.get("output");
        if (type == null) {
            throw new IllegalArgumentException("No specified output type");
        }

        var outputFile = arguments.get("file");
        if (outputFile == null) {
            throw new IllegalArgumentException("No specified output file");
        }

        return switch (type) {
            case "csv" -> CsvWriter.of(Path.of(outputFile));
            case "sqlite" -> SQLiteWriter.of(Path.of(outputFile));
            default -> throw new IllegalArgumentException("Unhandled output type");
        };
    }
}
