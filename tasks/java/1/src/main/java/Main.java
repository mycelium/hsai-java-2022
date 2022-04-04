import io.InputFile;
import io.OutputFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        InputFile input = null;
        do {
            try {
                System.out.print("Input file path: ");
//                input = new InputFile(sc.nextLine());
                input = new InputFile("C:\\Users\\Maksim\\Desktop\\test.txt");
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        } while (input == null);

        System.out.print("Output file path (optional): ");
        String outputPath = sc.nextLine();
        System.out.print("String of symbols to count (optional): ");
        String symbols = sc.nextLine();

        TreeMap<Integer, Integer> wordLengthCount = new TreeMap<>();
        LinkedHashMap<Character, Integer> symbolCount = new LinkedHashMap<>();

        int whitespaces = 0;
        int counter = 0;
        for (char c : input.read().toCharArray()) {
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
        wordLengthCount.merge(counter, 1, Integer::sum);
        wordLengthCount.remove(0);

        StringBuilder outputStr = new StringBuilder();
        outputStr.append("\nWords: ")
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

        if (outputPath.isEmpty()) {
            System.out.println(outputStr.toString());
        } else {
            new OutputFile(outputPath).write(outputStr.toString());
        }
    }
}
