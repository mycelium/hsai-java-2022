import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

class App{
    static private int wordCounter;
    static private long spaceCounter;
    static private Map<Integer, Integer> wordSizesCounter = new HashMap<>();
    static private String chars = null;
    static private Integer[] charCounter;
    static private String inputFile = null, outputFile = null;

    public static void main(String[] args) throws IOException {
        Options options = new Options();

        Option input = new Option("i", "input", true, "Input file path");
        options.addOption(input);

        Option output = new Option("o", "output", true, "Output file path");
        options.addOption(output);

        Option searchingWords = new Option("w", "search", true, "Search characters");
        options.addOption(searchingWords);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        inputFile = cmd.getOptionValue("input");
        outputFile = cmd.getOptionValue("output");
        chars = cmd.getOptionValue("search");
        if(chars != null) {
            StringBuilder sb = new StringBuilder();
            chars.chars().distinct().forEach(c -> sb.append((char) c));
            chars = sb.toString();
            charCounter = new Integer[chars.length()];
            for (int i = 0; i < chars.length(); i++) {
                charCounter[i] = 0;
            }
        }

        Path p;

        if (Files.exists(p = Paths.get(inputFile))) {
            String filetStr = Files.readString(p);
            String[] words = filetStr.split("\\W+");

            wordCounter = words.length;
            spaceCounter += filetStr.chars().filter(ch -> ch == ' ').count();

            for (String word : words) {
                wordSizesCounter.compute(word.length(), (k, v) -> (v == null) ? 1 : v + 1);
                if(chars != null)
                    for (int i = 0; i < chars.length() - 1; i++)
                        charCounter[i] += word.length() - word.replace(String.valueOf(chars.charAt(i)), "").length();
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Words count: %d\nSpaces count: %d\n", wordCounter, spaceCounter));

            if(chars != null) {
                for (int i = 0; i < charCounter.length; i++) {
                    stringBuilder.append(String.format("%s: %d\n", String.valueOf(chars.charAt(i)), charCounter[i]));
                }
            }
            stringBuilder.append(String.format("Words count by length:\n"));
            wordSizesCounter.forEach((k, v) -> {
                stringBuilder.append(String.format("%d: %d\n", k, v));
            });

            if(outputFile != null) {
                Path o;
                if (Files.exists(o = Paths.get(outputFile))){
                    try {
                        Files.writeString(o, stringBuilder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(stringBuilder);
                    System.out.println("Output file doesn't exist");
                }
            }
            else
                System.out.println(stringBuilder);
        }
        else{
            System.out.println("Input file doesn't exist");
        }
    }
}