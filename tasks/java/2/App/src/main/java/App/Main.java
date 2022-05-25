package App;

import Generator.Generator;
import Input.Input;
import Output.Writer;

import java.util.logging.Logger;


public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        log.info("Program started");

        Input input = new Input(args);
        Generator gen = input.generator();
        Writer writer = input.writer();
        writer.write(gen.genValues());

        log.info("Program finished");

    }
}
