package Output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVWriter extends Writer{
    public CSVWriter(String path) {
        super(path);
    }

    void checkOutputFile(){
        if (!Files.exists(Paths.get(pathToFile))) {
            log.info("Dir doesn't exist");
            try {
                Files.createDirectory(Paths.get(pathToFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Dir was created");
        }
        pathToFile = pathToFile + "\\base.csv";
        if (!Files.exists(Paths.get(pathToFile))) {
            try {
                Files.createFile(Paths.get(pathToFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("base.csv was created");
        }
    }

    @Override
    public void write(List<Double> list) {
        checkOutputFile();
        Path p = Paths.get(pathToFile);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach((elem) -> stringBuilder.append(elem).append(","));
        try {
            Files.writeString(p, stringBuilder);
        } catch (IOException e) {
            log.info("CSV write error");
        }
    }
}
