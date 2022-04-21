package com.spbstu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional<Path> out = Optional.empty();

        if (args.length == 0) {
            System.out.println("Input is empty");
        }

        Path in = Path.of(args[0]);
        if (args.length == 2) {
            out = Optional.of(Path.of(args[1]));
        }

        String input = Files.readString(in);
        String[] words = input.split("\\s+");

        long wordsCount = words.length;
        long spacesCount = input.codePoints().filter(c -> c == ' ').count();
        HashMap<Integer, Integer> lengthDict = new HashMap<>();

        for (String word : words) {
            lengthDict.compute(word.length(), (k, v) -> v == null ? 1 : ++v);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "Total words count: %d\n" +
                        "Spaces count: %d\n"
                , wordsCount, spacesCount)
        );
        lengthDict.forEach((k, v) -> {
            sb.append(String.format("Words with length %d - %d\n", k, v));
        });

        out.ifPresentOrElse( p-> {
            try {
                Files.writeString(p, sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(sb));

    }
}
