package spbstu.hsai.consoleGenerator.io.output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

public class CsvOutput extends Output {

    private PrintWriter writer;

    public CsvOutput(Path outputFile) {

        super(outputFile);
        try {
            this.writer = new PrintWriter(new FileOutputStream(outputFile.toFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        writer.close();
        logger.info("CSV writer closed");
    }

    @Override
    public void write(double value) {
        writer.println(value);
    }
}
