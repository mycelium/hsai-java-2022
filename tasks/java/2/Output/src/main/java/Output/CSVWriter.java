package Output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVWriter extends Writer{
    public CSVWriter(String path) {
        super(path);

        if (!Files.exists(Paths.get(pathToFile))) {
            log.info("CSV doesn't exist");
            System.exit(4);
        }
    }
    @Override
    public void write(String s) {
        Path p = Paths.get(pathToFile);
        try {
            Files.writeString(p, s);
        } catch (IOException e) {
            log.info("CSV write error");
        }
    }
}
