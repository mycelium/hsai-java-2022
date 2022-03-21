import java.util.HashMap;

public class Analyzer {
    public int wordsCount;
    public HashMap<Integer, Integer> wordsLength;
    int whitespaceCount;

    public void analyze(String textFromFile) {
        String[] words = getWords(textFromFile);
        wordsCount = words.length;
        wordsLength = getWordsLength(words);
        whitespaceCount = getWhitespaceCount(textFromFile);
    }

    private int getWhitespaceCount(String textFromFile) {
        return textFromFile.split("[\\S]+").length;
    }

    private HashMap<Integer, Integer> getWordsLength(String[] words) {
        HashMap<Integer, Integer> wordsLength = new HashMap<Integer, Integer>();
        for (String word : words) {
            if (!wordsLength.containsKey(word.length())) {
                wordsLength.put(word.length(), 1);
            } else {
                wordsLength.put(word.length(), wordsLength.get(word.length()) + 1);
            }
        }
        return wordsLength;
    }

    private String[] getWords(String textFromFile) {
        return textFromFile.split("[^A-Za-zА-Яа-я]+");
    }
}
