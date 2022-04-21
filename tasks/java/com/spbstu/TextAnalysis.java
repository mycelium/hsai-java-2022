package com.spbstu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextAnalysis {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) System.out.println("No input file name provided");

        Path inputFile = Path.of(args[0]);

        Path outputFile = null;
        if (args.length == 2) outputFile = Path.of(args[1]);

        String input = Files.readString(inputFile);
        String[] words = input.split("\\s+");

        long wordsNumber = words.length;
        long spacesNumber = input.codePoints().filter(c -> c == ' ').count();
        Map<Integer, Integer> lengthDictionary = new HashMap<>();

        for (String word : words) {
            lengthDictionary.compute(word.length(), (key, value) -> value == null ? 1 : ++value);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
            String.format("Total words count: %d\nSpaces count: %d\n", wordsNumber, spacesNumber)
        );
        lengthDictionary.forEach(
            (key, value) -> stringBuilder.append(String.format("Words with length %d — %d\n", key, value))
        );

        if (outputFile == null) {
            System.out.println(stringBuilder);
        } else {
            try {
                Files.writeString(outputFile, stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
