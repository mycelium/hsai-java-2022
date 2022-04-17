import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            System.out.println("No argument found");
            System.exit(0);
        }

        System.out.print("Input filename: " + args[0]);
        Path input_file = Paths.get(args[0]);

        if (!Files.exists(input_file)) {
            System.out.println("No file found");
            System.exit(0);
        }

        int unnecessarySpaces = Files.readAllLines(input_file).size() - 1;
        String file = String.valueOf(Files.readAllLines(input_file));
        String text = file.substring(1, file.length() - 1);

        // Count words
        String[] words = Analysis.splitText(text);
        int words_total = words.length;

        // Count spaces
        long spaces_total = Analysis.countSpaces(text) - unnecessarySpaces;

        // Count words' lengths and amount of them
        Map<Integer, Long> map = Analysis.wordSizeMap(words);

        System.out.println("\nNumber of words: " + words_total);
        System.out.println("Number of spaces: " + spaces_total + "\n");
        System.out.print("Word's lengths: ");
        map.forEach((key, value) -> System.out.println(value + " words of length " + key));
    }
}
