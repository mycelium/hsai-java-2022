import java.nio.file.Files;
import java.nio.file.Path;

public class Text {

    public String textFromFile;
    public String resultFilePath;

    public Text(String filePath, String resultFilePath) {
        try {
            textFromFile = readFileAsString(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.resultFilePath = checkPath(resultFilePath, filePath);
    }

    private static String checkPath(String path, String oridinalFilePath) {
        if (path.contains(".txt") && !path.equals(oridinalFilePath)) return path;
        else return "";
    }

    private static String readFileAsString(String fileName) throws Exception {
        String data;
        data = Files.readString(Path.of(fileName));
        return data;
    }
}
