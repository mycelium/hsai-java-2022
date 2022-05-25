package homework2.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CSV implements Output{
    private final File file;

    public CSV(File file){
        this.file = file;
    }

    public void write(ArrayList<Double> data) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        log.severe("Writing data to CSV...");
        writer.write(data.stream()
                .map(Object::toString)
                .collect(Collectors.joining(";"))
        );
        log.severe("Data successfully written to CSV");
        writer.close();
    }
}
