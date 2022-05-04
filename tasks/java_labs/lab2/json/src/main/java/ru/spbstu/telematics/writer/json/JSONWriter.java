package ru.spbstu.telematics.writer.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbstu.telematics.parameters.ResultParameters;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONWriter {

    private static Logger logger = LogManager.getLogger(JSONWriter.class);

    private Path path;

    public JSONWriter(String filePath) throws IOException {
        logger.info("Create JSONWriter for: " + filePath);
        path = createFileWithExtension(filePath, ".json");
    }

    public void write(List<ResultParameters> variablesList) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(path)));
        StringBuilder resultJSON = new StringBuilder("{\"variables\": [\n");
        String prefix = "";
        for (ResultParameters variable : variablesList) {
            resultJSON.append(prefix);
            resultJSON.append(variable.toJSON());
            prefix = ",\n";
        }
        resultJSON.append("]}");
        dataOutputStream.writeBytes(resultJSON.toString());
        dataOutputStream.close();
    }

    private Path createFileWithExtension(String filePath, String extension) throws IOException {
        Path path = Path.of(filePath);
        if (!filePath.endsWith(extension)) {
            IllegalArgumentException ex = new IllegalArgumentException("File must have" + extension + "extension");
            logger.error("File: " + filePath + "has not " + extension, ex);
            throw ex;
        }
        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            Files.createFile(path);
            logger.info("Create file: " + filePath);
        }
        return path;
    }
}