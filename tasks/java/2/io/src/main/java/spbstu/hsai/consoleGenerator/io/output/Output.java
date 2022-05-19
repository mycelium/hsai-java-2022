package spbstu.hsai.consoleGenerator.io.output;

import java.nio.file.Path;
import java.util.logging.Logger;

public abstract class Output {

    protected static final Logger logger = Logger.getLogger(Output.class.getName());
    protected Path outputFile;

    protected Output(Path outputFile) {
        this.outputFile = outputFile;
    }

    public abstract void close();

    public abstract void write(double value) throws Exception;

    public Path getOutputFile() {
        return outputFile;
    }
}
