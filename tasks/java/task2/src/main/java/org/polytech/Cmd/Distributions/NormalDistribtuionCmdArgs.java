package org.polytech.Cmd.Distributions;

import picocli.CommandLine;
public class NormalDistribtuionCmdArgs {
    @CommandLine.Option(names = "--normal-dist", required = true, description = "Normal distribution")
    public boolean normalDist;

    @CommandLine.Option(names = "--mean", description = "Mean of the normal distribution", required = true)
    public double mean;

    @CommandLine.Option(names = "--variance", description = "Variance of the normal distribution", required = true)
    public double variance;
}
