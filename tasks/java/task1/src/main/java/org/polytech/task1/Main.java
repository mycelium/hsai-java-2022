package org.polytech.task1;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class WCCmdArgs {
    @Parameter(names = "--help", help = true)
    public boolean help;

    @Parameter(description = "Input file path", required = true)
    public String InputFilePath;

    @Parameter(names = "--output", description = "Output file path")
    public String OutputFilePath;

    @Parameter(names = "--chars", description = "Which non-whitespace characters to count separately")
    private String charsToCountString = "";

    private Set<Character> charsToCountSet = null;

    public Set<Character> CharsToCount() {
        if (charsToCountSet == null) {
            charsToCountSet = charsToCountString.chars().mapToObj(ch -> (char) ch).collect(Collectors.toUnmodifiableSet());
        }
        return charsToCountSet;
    }
}

record WCParams(Set<Character> charsToCount) {
}

record Tuple<A, B>(A fst, B snd) {
}

record WCResult(Long spaceCount, Map<Integer, Long> wordPerLetterCount, Map<Character, Long> characterCounts) {
    public Long WordCount() {
        return wordPerLetterCount().values().stream().reduce(0L, Long::sum);
    }
}

class WC {

    private WCParams wcParams;

    public WC(WCParams wcArgs) {
        this.wcParams = wcArgs;
    }

    private static final List<Character> whitespaceEmptyList = List.of((char) 32);

    private Boolean isSpaceWord(List<Character> word) {
        return word.equals(whitespaceEmptyList);
    }

    private final Collector<List<Character>, ?, Map<Integer, Long>> wordsCountCollector =
            Collectors.mapping(
                    word -> word.stream().filter(Character::isAlphabetic).toList(),
                    Collectors.groupingBy(List::size, Collectors.counting())
            );

    private final Collector<List<Character>, ?, Map<Character, Long>> charactersCountCollector =
            Collectors.flatMapping(
                    str -> str.stream()
                            .filter(ch -> wcParams.charsToCount().contains(ch)),
                    Collectors.groupingBy(Function.identity(), Collectors.counting()));

    private final Collector<List<Character>, ?, Tuple<Map<Integer, Long>, Map<Character, Long>>> charCollectors =
            Collectors.filtering(word -> !isSpaceWord(word), Collectors.teeing(
                    wordsCountCollector,
                    charactersCountCollector,
                    Tuple::new
            ));

    private final Collector<List<Character>, ?, Long> spacesCollector =
            Collectors.filtering(this::isSpaceWord, Collectors.counting());


    private final Collector<List<Character>, ?, WCResult> wcCollector =
            Collectors.teeing(
                    spacesCollector,
                    charCollectors,
                    (spaces, characterWC) ->
                            new WCResult(spaces, characterWC.fst(), characterWC.snd())
            );

    public WCResult Run(String input) {
        return StreamEx.of(input.chars().mapToObj(ch -> (char) ch))
                // Split to whitespaces and words
                .collapse(
                        (character, character2) -> !character.equals(' ') && !character2.equals(' '),
                        Collectors.toList())
                .collect(wcCollector);
    }
}

public class Main {
    private static WCCmdArgs wcCmdArgs;

    private static String Render(WCResult wcResult) {
        var builder = new StringBuilder();
        builder.append("Word count: ").append(wcResult.WordCount().toString()).append('\n');
        builder.append("Space count: ").append(wcResult.spaceCount().toString()).append('\n');
        builder.append("Word count by length: ").append('\n');
        wcResult.wordPerLetterCount().forEach((wordLength, wordCount) ->
                builder.append(wordLength.toString()).append(" -> ").append(wordCount.toString()).append('\n')
        );
        if (!wcCmdArgs.CharsToCount().isEmpty()) {
            builder.append("Character counts: ").append('\n');
            wcResult.characterCounts().forEach((character, charCount) ->
                    builder.append(character).append(" -> ").append(charCount.toString()).append('\n')
            );
        }

        return builder.toString();
    }

    private static void Output(String outputStr) {
        if (wcCmdArgs.OutputFilePath != null) {
            try {
                Files.writeString(Path.of(wcCmdArgs.OutputFilePath), outputStr);
            } catch (IOException e) {
                System.out.println("Error while writing to output file: " + wcCmdArgs.OutputFilePath);
            }
        } else {
            System.out.println(outputStr);
        }
    }

    public static void main(String[] args) {
        wcCmdArgs = new WCCmdArgs();
        var jc = JCommander.newBuilder().addObject(wcCmdArgs).build();
        jc.parse(args);

        if (wcCmdArgs.help) {
            jc.usage();
            return;
        }

        var params = new WCParams(wcCmdArgs.CharsToCount());
        var WC = new WC(params);


        try {
            var input = new String(Files.readAllBytes(Path.of(wcCmdArgs.InputFilePath)));
            var wcResult = WC.Run(input);
            var outputStr = Render(wcResult);
            Output(outputStr);
        } catch (IOException exc) {
            System.out.println("Missing file: " + wcCmdArgs.InputFilePath);
        }
    }
}
