import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TextAnalysis {
    public static StringBuilder analyzeText(String inputText) {

        String[] words = inputText.split("\\s+");
        long spaces = inputText.codePoints().filter(i -> i == ' ').count();
        Map<Integer, Long> wordSizeOccurrence = Arrays.stream(words).collect(Collectors.
                groupingBy(String::length, Collectors.counting()));

        StringBuilder output = new StringBuilder();
        output.append(String.format(
                "Number of words: %d" + System.lineSeparator() + "Number of spaces: %d" +
                        System.lineSeparator() + "Word length with occurrence (length - occurrence): " +
                        System.lineSeparator(),
                words.length, spaces));

        wordSizeOccurrence.forEach((key, value) -> output.append(String.format("%d - %d" + System.lineSeparator(), key, value)));

        return output;
    }
}
