import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileHandler {
    public void createCSV(StringBuilder data) {
        String path;
        if (!Objects.equals(Main.out, "")) {
            path = Main.out;
        } else {
            path = System.getProperty("user.dir");
        }
        path += "/data.csv";
        try {
            FileWriter writer = new FileWriter(path);
            writer.append(data);
            writer.close();
            System.out.printf("\nFile successfully written to {%s}%n", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
