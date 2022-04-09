package text.analyzer.bean;

import java.util.List;
import java.util.Set;

public class DataLoad {
    private List<String> textInput;
    private String fileForOutput;
    private Set<Character> symbolsForSearching;

    public DataLoad() {
    }

    public List<String> getTextInput() {
        return textInput;
    }

    public void setTextInput(List<String> textInput) {
        this.textInput = textInput;
    }

    public String getFileForOutput() {
        return fileForOutput;
    }

    public void setFileForOutput(String fileForOutput) {
        this.fileForOutput = fileForOutput;
    }

    public Set<Character> getSymbolsForSearching() {
        return symbolsForSearching;
    }

    public void setSymbolsForSearching(Set<Character> symbolsForSearching) {
        this.symbolsForSearching = symbolsForSearching;
    }
}
