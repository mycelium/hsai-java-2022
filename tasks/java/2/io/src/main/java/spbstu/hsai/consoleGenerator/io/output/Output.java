package spbstu.hsai.consoleGenerator.io.output;

import java.util.logging.Logger;

public abstract class Output {

    protected static final Logger logger = Logger.getLogger(Output.class.getName());

    public abstract void close() throws Exception;
    public abstract void write(double value) throws Exception;
}
