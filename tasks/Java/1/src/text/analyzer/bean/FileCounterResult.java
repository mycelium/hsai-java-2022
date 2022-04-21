package text.analyzer.bean;

import java.util.Map;

public class FileCounterResult {
    private int wordsCount;
    private int spacesCount;
    private Map<Integer, Integer> wordsByLenCount;
    private Map<Character, Integer> symbolsCount;

    public FileCounterResult() {
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getSpacesCount() {
        return spacesCount;
    }

    public void setSpacesCount(int spacesCount) {
        this.spacesCount = spacesCount;
    }

    public Map<Integer, Integer> getWordsByLenCount() {
        return wordsByLenCount;
    }

    public void setWordsByLenCount(Map<Integer, Integer> wordsByLenCount) {
        this.wordsByLenCount = wordsByLenCount;
    }

    public Map<Character, Integer> getSymbolsCount() {
        return symbolsCount;
    }

    public void setSymbolsCount(Map<Character, Integer> symbolsCount) {
        this.symbolsCount = symbolsCount;
    }
}
