import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerateAndWrite {

    public void generateAndWrite(String fileAbsolute, int count, Double[] params, String type) {
        Path path = Paths.get(fileAbsolute);
        File file = new File(path + File.separator + "output.csv");
        if (file.exists()) {
            file.delete();
            System.out.println("Success delete old file.");
        }
        try {
            file.createNewFile();
            System.out.println("Success create output file.");
            FileWriter fw = new FileWriter(file);
            generateData(count, params, type, fw);
            System.out.println("Success generated file to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("‼️ " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateData(int count, Double[] params, String type, FileWriter fileWriter) throws IOException {
        Generators generators = new Generators(params[0], params[1]);
        List<Double> list;

        switch (type) {
            case "normal": {
                list = generators.generateNormal(count);
                break;
            }
            case "uniform": {
                list = generators.generateUniform(count);
                break;
            }
            case "poisson": {
                generators = new Generators(params[0], 0.0);
                list = generators.generatePoisson(count);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (list.size() == count) {
            System.out.println("Poisson distribution successfully generated.");
        } else {
            throw new InvalidObjectException("Error generating poisson distribution.");
        }
        list.forEach(e -> {
            try {
                fileWriter.append(e.toString()).append("\n");
            } catch (IOException ee) {
                System.out.println("‼️ " + ee.getMessage());
                ee.printStackTrace();
            }
        });
        fileWriter.close();
    }
}
