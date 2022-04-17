import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    private static final class Statistic {
        int wordsCount;
        long spacesCount;
        Map<Integer, Long> wordsCountByLength;

        @Override
        public String toString() {
            return "Statistic:\n" +
                "Words count: " + wordsCount + "\n" +
                "Spaces count: " + spacesCount + "\n" +
                "Words count by length: " + wordsCountByLength.entrySet().stream()
                    .map(x -> "\t" + x.getKey() + " - " + x.getValue())
                    .collect(Collectors.joining("\n")) + "\n";
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("This program must have at least one parameter!");
            System.exit(0);
        }

        Path in = Path.of(args[0]);
        if (!Files.exists(in))  {
            System.out.println("File args[0] doesn't exist!");
            System.exit(0);
        }
        Optional<Path> out = args.length == 2 ? Optional.of(Path.of(args[1])) : Optional.empty();

        String text = Files.readString(in);
        String[] words = text.split(" +");

        Statistic statistic = new Statistic();
        statistic.wordsCount = words.length;
        statistic.spacesCount = text.codePoints().filter(ch -> ch == ' ').count();
        statistic.wordsCountByLength = Arrays.stream(words).collect(Collectors.groupingBy(String::length, Collectors.counting()));

        out.ifPresentOrElse(path -> {
            try {
                Files.writeString(path, statistic.toString());
            } catch (IOException e) {
                System.out.println("Error writing to file!");
            }
        }, () -> System.out.println(statistic));
    }
}
