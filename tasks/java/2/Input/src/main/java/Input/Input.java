package Input;

import Generator.Generator;
import Generator.NormalGenerator;
import Generator.PoissonGenerator;
import Generator.UniformGenerator;

import Output.BaseWriter;
import Output.CSVWriter;
import Output.Writer;
import org.apache.commons.cli.*;

import java.util.logging.Logger;

public class Input {
    protected static final Logger log = Logger.getLogger(Input.class.getName());
    private String typeD;
    private int num;
    private String format;
    private String outputDir;
    private String params;
    public Input(String[] args){
        log.info("Parsing started");
        CommandLine cmd = null;
        Options opts = new Options();
        opts.addRequiredOption("t", "type", true, "Distribution type");

        Option paramsOpt = new Option("p", "parameters", true, "Distribution parameters");
//        paramsOpt.setArgs(2);
//        paramsOpt.setValueSeparator(',');
        paramsOpt.setRequired(true);
        opts.addOption(paramsOpt);

        opts.addRequiredOption("n", "number", true, "Number of values");
        opts.addRequiredOption("f", "format", true, "Output format");
        opts.addRequiredOption("o", "out", true, "Output file");

        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(opts, args);
        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Distributor", opts);
            System.exit(1);
        }


        typeD = cmd.getOptionValue('t');
        params = cmd.getOptionValue('p');
        num = Integer.valueOf(cmd.getOptionValue('n'));
        format = cmd.getOptionValue('f');
        outputDir = cmd.getOptionValue('o');


        log.info("Parsing finished");
    }

    public Generator generator() {
        log.info("Making generator started");
        if (num < 10000) {
            log.info("Too few values. Will be generated 10000 numbers");
            num = 10000;
        }
        Generator gen = null;
        if ("poisson".equals(typeD)) {
            if(params.length() > 0){
                String[]lines = params.split(",");
                gen = new PoissonGenerator(Double.valueOf(lines[0]), num);
            }
        }
        else if ("normal".equals(typeD)) {
            String[] lines = params.split(",");
            if (lines.length > 1) {
                gen = new NormalGenerator(Double.valueOf(lines[0]), Double.valueOf(lines[1]), num);
            } else if (params.length() > 0)
                gen = new NormalGenerator(Double.valueOf(lines[0]), 1, num);
            else
                gen = new NormalGenerator(1, 1, num);
        }
        else if ("uniform".equals(typeD)) {
            String[] lines = params.split(",");
            if (lines.length > 1) {
                gen = new UniformGenerator(Double.valueOf(lines[0]), Double.valueOf(lines[1]), num);
            } else if (params.length() > 0)
                gen = new UniformGenerator(Double.valueOf(lines[0]), 1, num);
            else
                gen = new UniformGenerator(0, 1, num);
        }
        else {
            log.info("Error type of distribution");
            System.exit(2);
        }
        log.info("Making generator finished");
        return gen;
    }

    public Writer writer(){
        log.info("Making writer started");
        Writer wr = null;
        if("csv".equals(format))
            wr = new CSVWriter(outputDir);
        else if("base".equals(format))
            wr = new BaseWriter(outputDir);
        else{
            log.info("Error format type");
            System.exit(3);
        }
        log.info("Making writer finished");
        return wr;
    }
}
