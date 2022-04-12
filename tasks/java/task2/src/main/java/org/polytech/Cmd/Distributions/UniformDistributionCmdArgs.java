package org.polytech.Cmd.Distributions;

import picocli.CommandLine;
public class UniformDistributionCmdArgs {
  @CommandLine.Option(names = "--uniform-dist", required = true, description = "Uniform distribution")
  public boolean uniformDist;

  @CommandLine.Option(names = {"--min"}, description = "min value of the uniform distribution", required = true)
  public double min;

  @CommandLine.Option(names = {"--max"}, description = "max value of the uniform distribution", required = true)
  public double max;
}
