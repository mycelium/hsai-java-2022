package text.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "Text-analyzer", mixinStandardHelpOptions = true,
    description = "This app can count words, spaces and chars")
public class App implements Runnable {

    @Parameters(index = "0", description = "input file")
    private Path in;

    @Option(names = {"-o", "--output"}, description = "file for output")
    private Path out;

    @Option(names = {"-c", "--chars"}, description = "chars for count (input without spaces)")
    private String characters;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            countAll();
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    private void countAll() throws IOException {
        if (!Files.exists(in)) {
            System.out.println("File <in> doesn't exist!");
        }

        final String text = Files.readString(in);

        String[] words = text.split(" +");
        long spacesCount = text.codePoints().filter(x -> x == ' ').count();

        Map<Integer, Long> wordsByLenCount = Arrays.stream(words)
            .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        Map<Character, Long> characterCount = Arrays.stream(characters.split(""))
            .filter(x -> x.length() == 1)
            .map(x -> x.charAt(0))
            .collect(Collectors.toMap(
                Function.identity(),
                x -> text.chars().filter(y -> y == x).count()));

        StringBuilder sb = new StringBuilder();
        sb.append("Words' count: ").append(words.length).append("\n")
            .append("Spaces' count: ").append(spacesCount).append("\n")
            .append("Words' by length count:\n")
            .append(wordsByLenCount.entrySet().stream()
                .map(x -> x.getKey() + " -> " + x.getValue()).collect(Collectors.joining("\n")))
            .append("\n")
            .append("Characters' count:\n")
            .append(characterCount.entrySet().stream()
                .map(x -> x.getKey() + " -> " + x.getValue()).collect(Collectors.joining("\n")));

        if (out != null) {
            Files.writeString(out, sb.toString());
        } else {
            System.out.println(sb);
        }
    }
}