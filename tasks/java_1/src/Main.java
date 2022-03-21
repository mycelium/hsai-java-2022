import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {

    private static Reader reader = new Reader();

    public static void main(String[] args) throws IOException {
        System.out.print("Enter source file path: ");
        String filePath = getFilePath();
        System.out.println("If you want to write result in file, enter result file path");
        String enteredChoice = reader.read();
        Text text = new Text(filePath, enteredChoice);
        Analyzer textAnalyzer = new Analyzer();
        textAnalyzer.analyze(text.textFromFile);
        String results = createResult(textAnalyzer);
        if (Objects.equals(text.resultFilePath, "")) {
            System.out.println(results);
        } else {
            outputToFile(results, text.resultFilePath);
        }
    }

    private static String createResult(Analyzer textAnalyzer) {
        StringBuilder result = new StringBuilder();
        result.append("Results:\n");
        result.append("Words in file: ").append(textAnalyzer.wordsCount);
        result.append("\nWhitespaces in file: ").append(textAnalyzer.whitespaceCount);
        result.append("\nWords by length (length of word - count)\n");
        for (var t : textAnalyzer.wordsLength.keySet()) {
            result.append(t).append("-").append(textAnalyzer.wordsLength.get(t)).append("\n");
        }
        return new String(result);
    }

    private static void outputToFile(String result, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(result);
        writer.close();
    }

    private static String getFilePath() {
        String path = reader.read();
        while (!path.contains(".txt") || path.equals("txt")) {
            System.out.println("Incorrect file path, try again");
            path = reader.read();
        }
        return path;
    }
}
