package org.polytech.task1;

import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class WC {
    record WCResult(Long spaceCount, Map<Integer, Long> wordPerLetterCount, Map<Character, Long> characterCounts) {
        public Long WordCount() {
            return wordPerLetterCount().values().stream().reduce(0L, Long::sum);
        }
    }

    record WCParams(Set<Character> charsToCount) {
    }

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

    private final Collector<List<Character>, ?, Utils.Tuple<Map<Integer, Long>, Map<Character, Long>>> charCollectors =
            Collectors.filtering(word -> !isSpaceWord(word), Collectors.teeing(
                    wordsCountCollector,
                    charactersCountCollector,
                    Utils.Tuple::new
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