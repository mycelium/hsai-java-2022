import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional<Path> out = Optional.empty();

        if (args.length == 0) {
            throw new Exception("Invalid input");
        }

        Path in = Path.of(args[0]);
        if (args.length == 2) {
            out = Optional.of(Path.of(args[1]));
        }

        String input = Files.readString(in);
        String[] words = input.split("\\s+");

        long wordsCount = words.length;
        long spacesCount = input.codePoints().filter(c -> c == ' ').count();
        HashMap<Integer, Integer> wordsLengthDict = new HashMap<>();

        for (String word : words) {
           wordsLengthDict.compute(word.length(), (k, v) -> v == null ? 1 : ++v);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Words count: %d\nSpaces count: %d\n", wordsCount, spacesCount));
        wordsLengthDict.forEach((k, v) -> {
            sb.append(String.format("Words with length %d - %d\n", k, v));
        });

        out.ifPresentOrElse( p-> {
            try {
                Files.writeString(p, sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(sb));
        
    }
}
