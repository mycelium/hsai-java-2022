import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.util.List;

public class Main {
    //Find words by splitting text
    public static List<String> splitText(String s) {
        String[] text_splitted = s.split("[\\s,!?.;:]+");
        List<String> words = new ArrayList<>();
        for (int i = 0; i < text_splitted.length; i++) {
            if (text_splitted[i].matches("[a-zA-Z]+"))
            {
                words.add(text_splitted[i]);
            }
        }
        return words;
    }

    // Count spaces in text
    public static int countSpaces(String text) {
        int spaceCount = 0;
        int i = 0;
        while (i < text.length()){
            char ch = text.charAt(i);
            if (ch == ' '){
                spaceCount++;
            }
            i++;
        }
        return spaceCount;
    }

    // Create dictionary with word lengths as keys and number of words as values
    public static Map<Integer, Integer> wordSizeMap(List<String> words) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0; i < words.size(); i++) {
            int len = words.get(i).length();
            if (map.containsKey(len)) {
                map.put(len, map.get(len) + 1);
            }
            else {
                map.put(len, 1);
            }
        }
        return map;
    }
    public static void main(String args[]) throws Exception {
        
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String input_file = reader.nextLine();
                
        File file = new File(input_file);
        try (Scanner sc = new Scanner(file)) {
            sc.useDelimiter("\\Z");
            String text = sc.next();
            List<String> words = splitText(text);
            Map<Integer, Integer> map = wordSizeMap(words);

            System.out.println("\nNumber of words: " + words.size());
            System.out.println("Number of spaces: " + countSpaces(text) + "\n");
            System.out.println("Word's lengths: ");
            map.forEach((key, value) -> System.out.println(value + " words of length " + key));
        }

        System.out.print("\nEnter list of symbols: ");
        String symbol_list = reader.nextLine();
        reader.close();
        System.out.print("Total number of symbols: " + symbol_list.length());
    }
  }
