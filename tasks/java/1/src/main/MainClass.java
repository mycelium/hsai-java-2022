package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MainClass {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No parameters!");
            return;
        }

        Path path = Paths.get(args[0]);
        if (!Files.exists(path)) {
            System.out.println("File doesn't exist!");
            return;
        }

        String textIn = new String(Files.readAllBytes(path));

        String[] words = textIn.split(" +");
        long spacesCount = textIn.codePoints().filter(x -> x == ' ').count();
        Map<Integer, Long> wordsByLengthCount = Arrays.stream(words)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "Words' count: %d\n" +
                "Spaces' count: %d\n" +
                "Words' by length count:\n", words.length, spacesCount
        ));
        wordsByLengthCount.forEach((k, v) -> result.append(
                String.format("%d -> %d\n", k, v)
        ));

        if (args.length == 2) {
            Files.write(Paths.get(args[1]), result.toString().getBytes());
        } else {
            System.out.println(result);
        }
    }
}
