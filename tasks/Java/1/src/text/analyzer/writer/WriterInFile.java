package text.analyzer.writer;

import java.io.FileWriter;
import java.io.IOException;

public class WriterInFile implements DataWriter {

    private final String fileForOutput;

    public WriterInFile(String fileForOutput) {
        this.fileForOutput = fileForOutput;
    }

    @Override
    public void write() {
        try (FileWriter writer = new FileWriter(fileForOutput, true)) {
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing to file! " + e.getMessage());
        }
    }

    @Override
    public void write(String text) {
        try (FileWriter writer = new FileWriter(fileForOutput, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing to file! " + e.getMessage());
        }
    }
}
