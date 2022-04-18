package com.spbstu;

import java.util.Map;

public class TextStatistics {
    long wordsCount;
    long spacesCount;
    Map<Integer, Integer> wordsLengthDict;

    TextStatistics(long wordsCount, long spacesCount, Map<Integer, Integer> wordsLengthDict) {
        this.wordsCount = wordsCount;
        this.spacesCount = spacesCount;
        this.wordsLengthDict = wordsLengthDict;
    }

    public StringBuilder build() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "Total words count: %d\nSpaces count: %d\n", this.wordsCount, this.spacesCount
                )
        );
        this.wordsLengthDict.forEach((k, v) -> {
            sb.append(String.format("Words with length %d - %d\n", k, v));
        });
        return sb;
    }
}
