package ru.spbstu.distr;

import me.tongfei.progressbar.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbstu.distr.core.AbstractGenerator;
import ru.spbstu.distr.io.AbstractExporter;
import ru.spbstu.distr.io.DataEntry;
import ru.spbstu.distr.io.InputParser;

public class ConsoleApp {
    public static void main(String[] args) {
        Logger log = LogManager.getLogger(ConsoleApp.class);

        InputParser inputParser = new InputParser(args);

        if (!inputParser.isValid()) {
            log.warn("Input error(s) detected. Stopping...");
            return;
        }

        AbstractGenerator generator = inputParser.getGenerator();
        AbstractExporter exporter = inputParser.getExporter();
        long number = inputParser.getNumber();

        ProgressBar pb = new ProgressBar("Calculating...", number);

        exporter.create();

        for (int i = 0; i < number; ++i) {
            DataEntry dataEntry = new DataEntry(i, generator.nextValue());
            exporter.write(dataEntry);
            pb.step();
        }
        pb.refresh();

        System.out.printf("\nGenerated values are stored at: %s", exporter.getOutputFile());
        exporter.close();
    }
}
