package text.analyzer.service;

import java.util.Map;

import text.analyzer.bean.FileCounterResult;

public class FIleCounterToStringService {

    public String resultToString(FileCounterResult fileCounterResult) {
        StringBuilder result = new StringBuilder();

        appendWithNewLine(result, "Begin"); 
        appendNewLine(result);

        appendWithNewLine(result, "Words count: " + fileCounterResult.getWordsCount());
        appendNewLine(result);

        appendWithNewLine(result, "Spaces count: " + fileCounterResult.getSpacesCount());
        appendNewLine(result);

        appendWithNewLine(result, "Words count by length: ");
        for (Map.Entry<Integer, Integer> entry: fileCounterResult.getWordsByLenCount().entrySet()) {
            appendWithNewLine(result, String.format(
                    "\t%d - %d word(s)", entry.getKey(), entry.getValue()
            ));
        }
        appendNewLine(result);

        if (fileCounterResult.getSymbolsCount() != null) {
            appendWithNewLine(result, "Symbols count by length: ");
            for (Map.Entry<Character, Integer> entry: fileCounterResult.getSymbolsCount().entrySet()) {
                appendWithNewLine(result, String.format(
                    "\t%c - %d item(s)", entry.getKey(), entry.getValue()
                ));
            }
            appendNewLine(result);
        }

        appendWithNewLine(result, "End");
        appendNewLine(result);
        return result.toString();
    }

    private void appendNewLine(StringBuilder sb) {
        sb.append("\n");
    }

    private void appendWithNewLine(StringBuilder sb, String text) {
        sb.append(text).append("\n");
    }
}
