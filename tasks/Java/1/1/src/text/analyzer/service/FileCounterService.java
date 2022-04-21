package text.analyzer.service;

import text.analyzer.bean.DataLoad;
import text.analyzer.bean.FileCounterResult;

import java.util.*;
import java.util.stream.Collectors;

public class FileCounterService {

    public FileCounterResult countAllSymbols(DataLoad dataLoad) {
        int wordsCount = 0;
        int spacesCount = 0;
        Map<Integer, Integer> wordsByLenCount = new HashMap<>();
        Map<Character, Integer> symbolsCount = initSymbolsCount(dataLoad.getSymbolsForSearching());

        for (String line: dataLoad.getTextInput()) {
            for (Character c: line.toCharArray()) {

                if (symbolsCount != null && symbolsCount.containsKey(c)) {
                    symbolsCount.merge(c, 1, Integer::sum);
                }

                if (c == ' ') {
                    spacesCount++;
                }
            }

            // space in the end of line
            spacesCount++;

            List<java.lang.String> words = Arrays.stream(line.split(" +"))
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toList());
            wordsCount += words.size();

            for (java.lang.String word: words) {
                Integer wordLen = word.length();
                if (wordsByLenCount.containsKey(wordLen)) {
                    wordsByLenCount.merge(wordLen, 1, Integer::sum);
                } else {
                    wordsByLenCount.put(wordLen, 1);
                }
            }
        }

        FileCounterResult result = new FileCounterResult();
        result.setWordsCount(wordsCount);
        result.setSpacesCount(spacesCount);
        result.setWordsByLenCount(wordsByLenCount);
        result.setSymbolsCount(symbolsCount);

        return result;
    }

    private Map<Character, Integer> initSymbolsCount(Set<Character> characterSet) {
        if (characterSet != null && !characterSet.isEmpty()) {
            Map<Character, Integer> result = new HashMap<>();

            for (Character c: characterSet) {
                result.put(c, 0);
            }

            return result;
        }

        return null;
    }
}
