package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputFile {
    private BufferedWriter bw;

    public OutputFile(String filename) throws IOException {
        bw = new BufferedWriter(new FileWriter(filename));
    }

    public void write(String str) throws IOException {
        bw.write(str);
        bw.close();
    }
}
