import java.util.*;
import java.util.stream.Collectors;

public class Analysis {
    //Find words by splitting text
    public static String[] splitText(String s) {
        String[] text_splitted = s.split("[,\\s!?;:]+");
        List<String> words = new ArrayList<>();
        for (int i = 0; i < text_splitted.length; i++) {
            if (text_splitted[i].matches("[a-zA-Z]+"))
            {
                words.add(text_splitted[i]);
            }
        }
        return words.toArray(new String[0]);
    }

    // Count spaces in text
    public static long countSpaces(String text) {
        return text.chars().filter(c -> c == (int)' ').count();
    }

    // Create dictionary with word lengths as keys and number of words as values
    public static Map<Integer, Long> wordSizeMap(String[] words) {
        return Arrays.stream(words)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
    }
}
