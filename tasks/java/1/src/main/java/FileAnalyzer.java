import java.io.*;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class FileAnalyzer {
    File inputFile;
    File outputFile;
    String symbols;

    TreeMap<Integer, Integer> wordLengthCount;
    LinkedHashMap<Character, Integer> symbolCount;
    int whitespaces;

    public boolean setInputFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        inputFile = file;
        return true;
    }

    public boolean setOutputFile(String path) {
        if (path.equals("")) { return true; }

        File file = new File(path);
        File dir = file.getParentFile();
        if (dir != null) {
            dir.mkdirs();
        }
        outputFile = file;
        return true;
    }

    public void setSymbolsToCount(String symbolString) {
        symbols = symbolString;
    }

    public void run() throws IOException {
        wordLengthCount = new TreeMap<>();
        symbolCount = new LinkedHashMap<>();
        whitespaces = 0;

        BufferedReader reader = Files.newBufferedReader(inputFile.toPath());
        String line = reader.readLine();
        int counter = 0;
        while (line != null) {
            for (char c : line.toCharArray()) {
                symbolCount.merge(c, 1, Integer::sum);
                if (Character.isAlphabetic(c) || (counter > 0 && (c == '-' || c == '\''))) {
                    counter++;
                } else {
                    if (c == ' ') {
                        whitespaces++;
                    }
                    wordLengthCount.merge(counter, 1, Integer::sum);
                    counter = 0;
                }
            }
            line = reader.readLine();
        }

        wordLengthCount.merge(counter, 1, Integer::sum);
        wordLengthCount.remove(0);

        print();
    }

    private void print() throws IOException {
        String res = generateOutputMessage();

        if (outputFile == null) {
            System.out.println(res);
        } else {
            Files.writeString(outputFile.toPath(), res);
        }
    }

    private String generateOutputMessage() {
        StringBuilder outputStr = new StringBuilder();
        outputStr.append("Words: ")
                .append(wordLengthCount.values().stream().mapToInt(Integer::intValue).sum());
        outputStr.append("\nWhitespaces: ")
                .append(whitespaces);

        outputStr.append("\n\nWord counts by length:\n");
        for (var entry : wordLengthCount.entrySet()) {
            outputStr.append("Of length ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append('\n');
        }

        if (!symbols.isEmpty()) {
            outputStr.append("\nSpecified symbols count:\n");
            for (char c : symbols.toCharArray()) {
                outputStr.append('\'')
                        .append(c)
                        .append("': ")
                        .append(symbolCount.getOrDefault(c, 0))
                        .append('\n');
            }
        }

        return outputStr.toString();
    }
}
