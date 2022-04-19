package org.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static record Statistic(long countWords, long countSpaces, Map<Integer, Long> countLengthWords) {
        @Override
        public String toString() {
            return "countWords: " + countWords + "\ncountSpaces: " + countSpaces +
                "\ncountLengthWords:\n" + countLengthWords.entrySet().stream()
                    .map(x -> x.getKey() + "->" + x.getValue())
                    .collect(Collectors.joining("\n"));
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("This program must have at least one parameter!");
            System.exit(0);
        }

        Path inputFile = Paths.get(args[0]);
        Path outputFile = args.length == 2 ? Paths.get(args[1]) : null;
        if (!Files.exists(inputFile))  {
            System.out.println("Input file doesn't exist");
            System.exit(0);
        }

        String text = new String(Files.readAllBytes(inputFile));
        String[] words = text.split(" +");

        long countWords = words.length;
        long countSpaces = text.codePoints().filter(ch -> ch == ' ').count();
        Map<Integer, Long> countLengthWords = Arrays.stream(words)
            .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        Statistic statistic = new Statistic(countWords, countSpaces, countLengthWords);

        if (outputFile != null) {
            Files.write(Paths.get(args[1]), statistic.toString().getBytes());
        } else {
            System.out.println(statistic);
        }
    }
}
