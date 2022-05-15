package ru.spbstu;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStat {
    CommandLine cmd;

    public static void main(String[] args) {
        TextStat ts = new TextStat();
        ts.cmd = ts.parseArgs(args);

        Path inputPath = Paths.get(ts.cmd.getOptionValue("i"));
        String content = null;
        try {
            content = Files.readString(inputPath);
        } catch (IOException e) {
            System.err.println("No such file: " + inputPath);
            return;
        }

        Result stats = ts.processText(content);
        String report = ts.generateReport(stats);

        if (ts.cmd.hasOption("o")) {
            Path outputPath = Paths.get(ts.cmd.getOptionValue("o"));
            try {
                Files.write(outputPath, report.getBytes());
            } catch (IOException e) {
                System.err.println("Output to file failed: " + outputPath);
            }
        } else {
            System.out.println(report);
        }
    }

    private CommandLine parseArgs(String[] args) {
        Options opts = new Options();
        opts.addRequiredOption("i", "input", true, "Input file");
        opts.addOption("o", "output", true, "Output file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(opts, args);
        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("TextStat", opts);
            System.exit(1);
        }
        return cmd;
    }

    private Result processText(String text) {
        long wordCount = Stream.of(text.split("[\\n\\r\\s]+")).count();
        long spacesCount = text.chars().mapToObj(c -> (char) c).filter(c -> c == ' ').count();
        Set<Character> specialSymbols = Stream.of('?', '!', '.', ',', ':', ';', '\"', '\'', '(', ')')
                .collect(Collectors.toCollection(HashSet::new));
        String clearedText = text.chars().mapToObj(c -> (char) c).filter(c -> !specialSymbols.contains(c))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
        Map<Integer, Long> freq = Stream.of(clearedText.split("[\\n\\r\\s]+")).map(String::length).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new Result(wordCount, spacesCount, freq);
    }

    private String generateReport(Result stats) {
        StringBuilder report = new StringBuilder(String.format("Words: %d\nSpaces: %d\nLength frequencies:\n", stats.words, stats.spaces));
        stats.freq.forEach((len, count) -> report.append(String.format("%d letter" + (len > 1 ? "s" : "") + ":\t%d time" + (count > 1 ? "s" : "") + "\n", len, count)));
        return report.toString();
    }

    record Result(Long words, Long spaces, Map<Integer, Long> freq) {
    }
}
