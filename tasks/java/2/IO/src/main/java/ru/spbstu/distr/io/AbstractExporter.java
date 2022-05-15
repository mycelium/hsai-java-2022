package ru.spbstu.distr.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractExporter {
    protected final Path outputFile;

    protected final Logger log;

    protected AbstractExporter(Path outputFile) {
        this.outputFile = outputFile;
        this.log = LogManager.getLogger(AbstractExporter.class);
    }

    public void create() {
        Path outputDir = outputFile.getParent();
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectory(outputDir);
            } catch (IOException e) {
                log.error("Unable to create output directory: {}", outputDir);
            }
        }
    }

    public abstract void close();

    public abstract void write(DataEntry entry);

    public Path getOutputFile() {
        return outputFile;
    }
}
