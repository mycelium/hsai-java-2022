import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Incorrect input parameters");
            return;
        }
        Path path = Paths.get(args[0]);
        if (!Files.exists(path)) {
            System.out.println("Can't find text file");
            return;
        }
        String stringInput = new String(Files.readAllBytes(path));
        Pattern pattern = Pattern.compile("\\s*(\\s|,|!|\\.|\\?|:|;)\\s*");
        String[] words = pattern.split(stringInput);
        long spacesCount = stringInput.codePoints().filter(e -> e == ' ').count();
        long wordsCount = words.length;
        Map<Integer, Long> countOfLengthsDict = Arrays.stream(words).collect(Collectors.groupingBy(String::length, Collectors.counting()));
        String result = configureOutput(countOfLengthsDict, spacesCount, wordsCount );
        if (args.length == 1) {
            System.out.println(result);
        }
        if (args.length == 2) {
            Files.write(Paths.get(args[1]), result.getBytes());
        }
    }
    public static String configureOutput(Map<Integer, Long> wordsDict, long spaces, long words){
        StringBuilder output = new StringBuilder();
        output.append(String.format("Total word count: %d \nTotal space count: %d \n", words, spaces));
        wordsDict.forEach((key, value) -> output.append(String.format("Number of words with %d letter(-s): %d\n", key, value)));
        return output.toString();
    }
}
