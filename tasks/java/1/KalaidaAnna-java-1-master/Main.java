import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 
public class Main {
    private static Path readPath(Scanner input) {
        Path file;
        do {
            System.out.println("Enter file's path: ");
            file = Path.of(input.next().trim());
        } while (!Files.exists(file));
        return file;
    }
 
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter path to input file: ");
        Path inputFile = readPath(input);
        Path outputFile = null;
 
        System.out.println("Write to console(y/n): ");
        if ("n".equals(input.next())) {
            System.out.println("Enter path to output file: ");
            outputFile = Path.of(input.next());
        }
 
        List<String> lines = Files.readAllLines(inputFile);
        Map<Integer, Integer> wordsSizeCounts = new HashMap<>();
        int wordsCount = 0;
        int spacesCount = 0;
        for (var line :
                lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ' ') {
                    spacesCount += 1;
                }
            }
 
            String[] words = line.split(" ");
            wordsCount += words.length;
            for (String word : words) {
                if (wordsSizeCounts.containsKey(word.length())) {
                    wordsSizeCounts.put(word.length(), wordsSizeCounts.get(word.length()) + 1);
                } else {
                    wordsSizeCounts.put(word.length(), 1);
                }
            }
        }
 
        StringBuilder result = new StringBuilder();
 
        result.append("Words in input file: ").append(wordsCount).append('\n');
        result.append("Spaces in input file: ").append(spacesCount).append('\n');
        result.append("Counts by words' sizes: \n");
        for (var keyValue :
                wordsSizeCounts.entrySet()) {
            result.append(keyValue.getKey()).append(" - ").append(keyValue.getValue()).append('\n');
        }
 
        if (outputFile == null) {
            System.out.println(result);
        } else {
            Files.writeString(outputFile, result);
        }
    }
}
