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
    private static class Statistics {
        long wordNum;
        long spaceNum;
        Map<Integer, Integer> wordsOfSizes;
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Parameters required: input file, output file (optional)");
            return;
        }

        Optional<Path> output = Optional.empty();
        Path input = Path.of(args[0]);
        if (args.length == 2) {
            output = Optional.of(Path.of(args[1]));
        }

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        StringBuilder sb1 = new StringBuilder();
        String line;
        while( (line = br.readLine()) != null) {
            sb1.append(line);
        }
        String inputString = sb1.toString().replaceAll("[^a-zA-Zа-яёА-ЯЁ \\-]", "");

        Statistics stats = new Statistics();
        String[] words = inputString.split("\\s+");
        stats.wordNum = words.length;
        stats.spaceNum = inputString.codePoints().filter(ch -> ch == ' ').count();
        stats.wordsOfSizes = new HashMap<>();

        for (String word : words) {
            stats.wordsOfSizes.compute(word.length(), (k, v) -> (v == null) ? 1 : v + 1);
        }

        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.format("Words total: %d\nSpaces total: %d\nWords number by length:\n",
                stats.wordNum, stats.spaceNum));
        stats.wordsOfSizes.forEach((k, v) -> {
            sb2.append(String.format("%d - %d\n", k, v));
        });

        output.ifPresentOrElse(p -> {
            try {
                Files.writeString(p, sb2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(sb2));
    }
}
