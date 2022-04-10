import java.util.*;
import java.io.File;

public class Main {

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input file path: ");
        String input_file = scanner.nextLine();

        File file = new File(input_file);
        try (Scanner scan = new Scanner(file)) {
            scan.useDelimiter("\\Z");
            String text = scan.next();
            List<String> words = FileAnalyzer.splitText(text);
            Map<Integer, Integer> map = FileAnalyzer.countWordSize(words);

            System.out.println("\nNumber of words: " + words.size());
            System.out.println("Number of spaces: " + FileAnalyzer.countSpaces(text) + "\n");
            System.out.print("Word's lengths: ");
            map.forEach((key, value) -> System.out.print("\n" + value + " word(s) of length " + key));
        }

        System.out.print("\nEnter list of symbols: ");
        String symbol_list = scanner.nextLine();
        scanner.close();
        System.out.print("Found symbols with next frequency: " + FileAnalyzer.countBySymbols(symbol_list));
    }
}