import java.util.*;
import java.io.File;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner scan_read = new Scanner(System.in);
        System.out.print("Please enter input file name: ");
        String input_file = scan_read.nextLine();

        File file = new File(input_file);
        try (Scanner sc = new Scanner(file)) {
            sc.useDelimiter("\\Z");
            String text = sc.next();
            List<String> words =  Analyzer.splitText(text);
            Map<Integer, Integer> map = Analyzer.wordSizeMap(words);

            System.out.println("***************************************************************");
            System.out.println("Count of words: " + words.size());
            System.out.println("Count of spaces: " +  Analyzer.countSpaces(text));
            System.out.println("***************************************************************");
            System.out.println("Length of words: ");
            map.forEach((key, value) -> System.out.println(value + " word(s) of length " + key));
        }

        System.out.println("***************************************************************");
        System.out.print("Please enter list of symbols: ");
        String symbol_list = scan_read.nextLine();
        scan_read.close();
        System.out.print("Count of symbols: " +  Analyzer.symbolsMap(symbol_list));
    }
}