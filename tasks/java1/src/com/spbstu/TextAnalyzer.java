package com.spbstu;

import java.util.HashMap;

public class TextAnalyzer {

    public static TextStatistics analyze(String text) {
        String[] words = text.split("\\s+");

        long wordsCount = words.length;
        long spacesCount = text.codePoints().filter(c -> c == ' ').count();
        HashMap<Integer, Integer> lengthDict = new HashMap<>();

        for (String word : words) {
            lengthDict.compute(word.length(), (k, v) -> v == null ? 1 : ++v);
        }
        return new TextStatistics(wordsCount, spacesCount, lengthDict);
    }
}
