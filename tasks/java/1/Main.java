import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String input_file = reader.nextLine();

        File file = new File(input_file);
        try (Scanner sc = new Scanner(file)) {
            sc.useDelimiter("\\Z");
            String text = sc.next();
            List<String> words = Analysis.splitText(text);
            Map<Integer, Integer> map = Analysis.wordSizeMap(words);

            System.out.println("\nNumber of words: " + words.size());
            System.out.println("Number of spaces: " + Analysis.countSpaces(text) + "\n");
            System.out.print("Word's lengths: ");
            map.forEach((key, value) -> System.out.println(value + " words of length " + key));
        }

        System.out.print("\nEnter list of symbols: ");
        String symbol_list = reader.nextLine();
        reader.close();
        System.out.print("Total number of symbols: " + Analysis.symbolsMap(symbol_list));
    }
}
