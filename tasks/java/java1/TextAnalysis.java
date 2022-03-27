import java.util.*;

public class TextAnalysis {

    public static int countWordsUsingSplit(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        String[] words = str.split("\\s+");

        return words.length;
    }

    public static int countSpaces(String str) {
        int spaceCount = 0;
        char[] ch = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            if (Character.isSpaceChar(ch[i])) {
                spaceCount++;
            } else {
                continue;
            }
        }

        return spaceCount;
    }

    // count word length with occurrence
    public static Map<Integer, Integer> countWordLengthOccurrence(List<String> words) {
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

    public static List<String> characterCount(char[] strArray)
    {
        // Creating a HashMap containing char as a key and occurrences as  a value
        HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();

        // checking each char of strArray
        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {
                // If char is present in charCountMap,
                // incrementing it's count by 1
                charCountMap.put(c, charCountMap.get(c) + 1);
            }
            else {
                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                charCountMap.put(c, 1);
            }
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry entry : charCountMap.entrySet()) {
            result.add(entry.getKey() + "=" + entry.getValue());
        }
        return result;
    }
}