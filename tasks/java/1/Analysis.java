import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {
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

    // Create dictionary with symbols as keys and amount of appearance as values
    public static Map<Character, Integer> symbolsMap(String text) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char[] textArray = text.toCharArray();

        for (int i = 0; i < textArray.length; i++) {
            if (map.containsKey(textArray[i])) {
                map.put(textArray[i], map.get(textArray[i]) + 1);
            }
            else {
                map.put(textArray[i], 1);
            }
        }
        return map;
    }
}
