package org.polytech.Cmd.Distributions;

import picocli.CommandLine;
public class PoissonDistributionCmdArgs {
    @CommandLine.Option(names = "--poisson-dist", required = true, description = "Poisson distribution")
    public boolean poissonDist;

    @CommandLine.Option(names = "--lambda", description = "lambda parameter for poisson", required = true)
    public double lambda;
}
