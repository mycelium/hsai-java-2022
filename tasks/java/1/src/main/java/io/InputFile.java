package io;

import java.io.*;
import java.nio.Buffer;

public class InputFile {
    private BufferedReader br;

    public InputFile(String filename) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(filename));
    }

    public String read() throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
