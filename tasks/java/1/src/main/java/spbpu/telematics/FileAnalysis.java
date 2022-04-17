package spbpu.telematics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileAnalysis {
    final private String readFile;
    final private String writeFile;

    public FileAnalysis(final String readFile, final String writeFile) {
        this.readFile = readFile;
        this.writeFile = writeFile;
    }

    public void startAnalysis() {
        try {
            Map<Integer, Long> nLetterWords = new TreeMap<>(Files.lines(Paths.get(readFile))
                    .map(line -> line.split("[ ,.!?—:;\\r\\n]"))
                    .flatMap(Arrays::stream)
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.groupingBy(String::length,
                            Collectors.counting())));

            int wordsNumber = (int) Files.lines(Paths.get(readFile))
                    .map(line -> line.split("[ ,.!?—:;\\r\\n]"))
                    .flatMap(Arrays::stream)
                    .filter(word -> !word.isEmpty()).count();

            List<String> lines = Files.lines(Paths.get(readFile)).collect(Collectors.toList());
            long spacesNumber = 0;
            for (String line : lines) {
                spacesNumber += line.chars().filter((c) -> c == (int) ' ').count();
            }

            StringBuilder results = new StringBuilder(String.format("Количество слов: %d\nКоличество пробелов: %d\n", wordsNumber, spacesNumber));
            for (Map.Entry<Integer, Long> entry : nLetterWords.entrySet()) {
                results.append(String.format("Количество слов из %d букв: %d\n", entry.getKey(), entry.getValue()));
            }
            printResults(results);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printResults(StringBuilder res) {
        if (writeFile != null) {
            try {
                Files.write(Paths.get(writeFile), res.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(res.toString());
        }
    }
}
