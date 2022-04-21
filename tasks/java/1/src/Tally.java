import java.util.HashMap;
import java.util.Map;

public class Tally {
    private int wordCount;
    public int getWordCount() {
        return wordCount;
    }
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    private int spaceCount;
    public int getSpaceCount() {
        return spaceCount;
    }
    public void setSpaceCount(int spaceCount) {
        this.spaceCount = spaceCount;
    }

    private Map<Integer, Integer> lengthCount = new HashMap<>();
    public Map<Integer, Integer> getLengthCount() {
        return lengthCount;
    }
    public void setLengthCount(Map<Integer, Integer> lengthCount) {
        this.lengthCount = lengthCount;
    }
}
