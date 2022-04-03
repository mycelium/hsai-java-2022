import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    private static class Statistic {
        long wordCount;
        long spaceCount;
        Map<Integer, Integer> wordSizesCount;
    }

    public static void main(String[] args) throws IOException {
        Optional<Path> out = Optional.empty();

        if (args.length < 1) {
            System.out.println("pass parameters infile [outfile]");
            return;
        }

        Path in = Path.of(args[0]);
        if (args.length == 2) {
            out = Optional.of(Path.of(args[1]));
        }

        String inputStr = Files.readString(in);
        String[] words = inputStr.split("\\s+");
        Statistic statistic = new Statistic();
        statistic.wordCount = words.length;
        statistic.spaceCount = inputStr.codePoints().filter(ch -> ch == ' ').count();
        statistic.wordSizesCount = new HashMap<>();

        for (String word : words) {
            statistic.wordSizesCount.compute(word.length(), (k, v) -> (v == null) ? 1 : v + 1);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Words' count: %d\nSpaces' count: %d\nWords' count by length:\n",
                statistic.wordCount, statistic.spaceCount));
        statistic.wordSizesCount.forEach((k, v) -> {
            stringBuilder.append(String.format("\t%d - %d\n", k, v));
        });

        out.ifPresentOrElse(p -> {
            try {
                Files.writeString(p, stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(stringBuilder));
    }
}
