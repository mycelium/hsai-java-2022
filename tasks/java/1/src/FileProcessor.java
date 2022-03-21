import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessor {
    private final boolean traced;
    private String report = "";
    private final File sourceFile;
    private final File traceFile;
    private final String text;
    private final String chars;

    public FileProcessor(String source, String trace, String chars) throws IOException {
        this.sourceFile = new File(source);
        this.chars = chars;
        if ("".equals(trace)) {
            this.traced = false;
            this.traceFile = null;
        } else {
            this.traced = true;
            this.traceFile = new File(trace);
            if (!this.traceFile.exists()) {
                this.traceFile.createNewFile();
            }
        }
        final Scanner sc = new Scanner(this.sourceFile);
        sc.useDelimiter("\\Z");
        this.text = sc.next();
        sc.close();
    }

    public boolean isTraced() {
        return traced;
    }

    public String getReport() {
        if ("".equals(report)) {
            buildReport();
        }
        return report;
    }

    private long countWords() {
        return Arrays.stream(text.split("[^a-zA-Z]+")).filter(el -> el.matches("[a-zA-Z]+")).count();
    }

    private long countSpaces() {
        return Arrays.stream(text.split("")).filter(el -> el.matches("\\s")).count();
    }

    private TreeMap<Integer, Long> countWordsByLetters() {
        TreeMap<Integer, Long> wordsByLetters = new TreeMap<>();
        for (String el : Arrays.stream(text.split("[^a-zA-Z]+"))
                .filter(el -> el.matches("[a-zA-Z]+")).collect(Collectors.toList())) {
            int length = el.length();

            if (wordsByLetters.containsKey(length)) {
                wordsByLetters.replace(length, wordsByLetters.get(length) + 1);
            } else {
                wordsByLetters.put(length, 1L);
            }
        }

        return wordsByLetters;
    }

    private TreeMap<String, Long> countByCharacters() {
        TreeMap<String, Long> counted = new TreeMap<>();
        for (String el : text.split("")) {
            if (chars.contains(el)) {
                if (counted.containsKey(el)) {
                    counted.replace(el, counted.get(el) + 1);
                } else {
                    counted.put(el, 1L);
                }
            }
        }

        return counted;
    }

    private void buildReport() {
        report += "-----------Processed File Report-----------\n\n" + "Path: " + sourceFile.getAbsolutePath()
                + "\n\nFound words: " + countWords()
                + "\nFound spaces: " + countSpaces() + "\nFound words by letters:\n[";

        final TreeMap<Integer, Long> wordsByLetters = new TreeMap<>(countWordsByLetters());
        wordsByLetters.forEach((quan, cnt) -> report += "\n     Letters: " + quan + ", Count: " + cnt);
        report += "\n]";

        if (!"".equals(chars)) {
            report += "\nFound characters in sequence {" + chars + "}:\n[";
            final TreeMap<String, Long> counted = new TreeMap<>(countByCharacters());
            counted.forEach((ch, cnt) -> report += "\n     Character: '" + ch + "', Count: " + cnt);
            report += "\n]";
        }

        report += "\n\n------------End Of File Report-------------";
    }

    public String traceToFile() throws IOException {
        if ("".equals(report)) {
            buildReport();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile));
        writer.write(report);
        writer.close();

        return "Report is traced to: " + traceFile.getAbsolutePath();
    }
}
