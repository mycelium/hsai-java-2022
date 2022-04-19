package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            Path path = Paths.get(args[0]);
            if (Files.exists(path)) {
                String inputText = new String(readAllBytes(path));
                String[] words = inputText.split("\\s+");

                long spaces = inputText.codePoints().filter(x -> x == ' ').count();
                Map<Integer, Long> wordsByLengthCount;
                wordsByLengthCount = Arrays.stream(words)
                        .collect(Collectors.groupingBy(String::length, Collectors.counting()));

                StringBuilder result = new StringBuilder(format("Слов %s\n Пробелов %s\n Слов длины n \n", words.length, spaces));

                wordsByLengthCount.forEach((k, v) -> result.append(format("%s ---- %s\n", k, v)));

                if (args.length == 2) Files.write(Paths.get(args[1]), result.toString().getBytes());
                else System.out.println(result);

            } else {
                System.out.println("Нет такого файла");
                return;
            }
        } else {
            System.out.println("Параметров не найдено");
            return;
        }

    }
}