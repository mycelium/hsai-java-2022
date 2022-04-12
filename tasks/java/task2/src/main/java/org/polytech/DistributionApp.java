package org.polytech;

import org.polytech.Cmd.Distributions.DistributionArgs;
import org.polytech.Output.OutputType;
import org.polytech.Utils.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.util.logging.LogManager;

// Main app for generating distribution and save it

@CommandLine.Command(name = "double", description = "Generate double distribution", mixinStandardHelpOptions = true)
public class DistributionApp implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    public int number;

    @CommandLine.Option(names = {"-n", "--number"}, description = "Number of values to generate, minimum 10000", required = true)
    public void setNumber(int number) {
        if (number >= 10000) {
            this.number = number;
        } else {
            Logger.log("Number must be greater than 1000");
            throw new CommandLine.ParameterException(spec.commandLine(), "Number must be greater than 1000");
        }
    }

    @CommandLine.Option(names = {"-op", "--output_path"}, description = "Output file path", required = true)
    public String outputPath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output type", required = true)
    public OutputType outputType;

    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    public DistributionArgs distributionArgs;

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        System.exit(new CommandLine(new DistributionApp()).execute(args));
    }

    public void run() {
        Logger.setLogFolder(outputPath);
        var output = outputType.toOutput().apply(outputPath);
        var generator = distributionArgs.toGenerator();
        var values = generator.generate(number);
        Logger.log("Generated " + values.size() + " values");
        try {
            output.save(values);
            Logger.log("Save distribution in " + outputPath);
        } catch (IOException e) {
            var msg = "Error while saving file: " + e.getMessage();
            Logger.log(msg);
            System.out.println(msg);
        }

    }

}

