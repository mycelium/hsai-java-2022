import java.util.*;

public class FileAnalyzer {

    public static List<String> splitText(String s) {
        String[] text_split = s.split("[\\s,!?.;:]+");
        List<String> words = new ArrayList<>();
        for (int i = 0; i < text_split.length; i++) {
            if (text_split[i].matches("[a-zA-Z]+"))
            {
                words.add(text_split[i]);
            }
        }
        return words;
    }

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

    public static Map<Integer, Integer> countWordSize(List<String> words) {
        Map<Integer, Integer> hashmap = new HashMap<Integer, Integer>();

        for (int i = 0; i < words.size(); i++) {
            int word_len = words.get(i).length();
            if (hashmap.containsKey(word_len)) {
                hashmap.put(word_len, hashmap.get(word_len) + 1);
            }
            else {
                hashmap.put(word_len, 1);
            }
        }
        return hashmap;
    }

    public static Map<Character, Integer> countBySymbols(String list) {
        Map<Character, Integer> hashmap = new HashMap<Character, Integer>();
        char[] symbolsArr = list.toCharArray();

        for (int i = 0; i < symbolsArr.length; i++) {
            if (hashmap.containsKey(symbolsArr[i])) {
                hashmap.put(symbolsArr[i], hashmap.get(symbolsArr[i]) + 1);
            }
            else {
                hashmap.put(symbolsArr[i], 1);
            }
        }
        return hashmap;
    }
}