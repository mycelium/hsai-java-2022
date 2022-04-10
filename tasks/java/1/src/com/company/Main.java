package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    private static class Stats {
        long cWords;
        long cWSpace;
        Map<Integer, Integer> cWSize;
    }

    public static void main(String[] args) throws IOException { //input may be "data/test.txt"

        if (args.length < 1) {
            System.out.println("Need parameters: input file (optional, output file)");
            return;
        }

        Optional<Path> output = Optional.empty();
        Path input = Path.of(args[0]);
        if (args.length == 2) {
            output = Optional.of(Path.of(args[1]));
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
        StringBuilder stringBuilder1 = new StringBuilder();
        String line;
        while( (line = bufferedReader.readLine()) != null) {
            stringBuilder1.append(line);
        }
        String inputString = stringBuilder1.toString().replaceAll("[^a-zA-Zа-яёА-ЯЁ \\-]", "");

        Stats stats = new Stats();
        String[] words = inputString.split("\\s+");
        stats.cWords = words.length;
        stats.cWSpace = inputString.codePoints().filter(ch -> ch == ' ').count();
        stats.cWSize = new HashMap<>();

        for (String word : words) {
            stats.cWSize.compute(word.length(), (k, v) -> (v == null) ? 1 : v + 1);
        }

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(String.format("Words count: %d\nSpaces count: %d\nWords count by length:\n",
                stats.cWords, stats.cWSpace));
        stats.cWSize.forEach((k, v) -> {
            stringBuilder2.append(String.format("%d - %d\n", k, v));
        });

        output.ifPresentOrElse(p -> {
            try {
                Files.writeString(p, stringBuilder2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(stringBuilder2));
    }
}
