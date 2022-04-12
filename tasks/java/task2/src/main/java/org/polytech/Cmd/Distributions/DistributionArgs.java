package org.polytech.Cmd.Distributions;

import org.polytech.Generators.Generator;
import org.polytech.Generators.NormalDistribution;
import org.polytech.Generators.PoissonDistribution;
import org.polytech.Generators.UniformDistribution;
import org.polytech.Utils.Logger;
import picocli.CommandLine;

public class DistributionArgs {
    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
    public NormalDistribtuionCmdArgs normalDistributionArgs;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
    public UniformDistributionCmdArgs uniformDistributionArgs;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "1")
    public PoissonDistributionCmdArgs poissonDistributionArgs;

    public Generator toGenerator() {
        if (normalDistributionArgs != null) {
            Logger.log("Normal distribution selected");
            return new NormalDistribution(normalDistributionArgs.variance, normalDistributionArgs.mean);
        }
        if (uniformDistributionArgs != null) {
            Logger.log("Uniform distribution selected");
            return new UniformDistribution(uniformDistributionArgs.min, uniformDistributionArgs.max);
        }
        if (poissonDistributionArgs != null) {
            Logger.log("Poisson distribution selected");
            return new PoissonDistribution(poissonDistributionArgs.lambda);
        }

        Logger.log("No distribution selected");
        throw new IllegalArgumentException("No distribution selected");
    }
}
