package Output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter implements Writer {

    private static final Logger log;

    static {
        log = Logger.getLogger(CSVWriter.class.getName());
    }

    private final String filePath;

    public CSVWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(List<Double> data) {
        StringBuilder stringBuilder = new StringBuilder();
        data.forEach((s) -> stringBuilder.append(s).append(","));
        try {
            Files.writeString(Path.of(filePath), stringBuilder);
            log.info("Data written to CSV file");
        } catch (IOException ex) {
            log.severe("Error writing to CSV" + ex.getMessage());
        }
    }
}
