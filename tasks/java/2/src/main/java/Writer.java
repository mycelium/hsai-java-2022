import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Writer {

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();

    public void setFileData(String fileAbsolute, int count, Double[] params, String type) {
        Path path = Paths.get(fileAbsolute);
        String fileName = "output.csv";
        File file = new File(path + File.separator + fileName);
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
            exceptionHandler.stop(e.getMessage());
        }
    }

    private void generateData(int count, Double[] params, String type, FileWriter fileWriter) throws IOException {
        Generators generators;
        List<String> list = new ArrayList<>();
        boolean flag = false;
        logSelect(type, flag, null);
        switch (type) {
            case "normal" -> {
                generators = new Generators(params[0], params[1]);
                list = generators.generateNormal(count);
                flag = true;
                logSelect(type, flag, Collections.singletonList(list));
            }
            case "uniform" -> {
                generators = new Generators(params[0], params[1]);
                list = generators.generateUniform(count);
                flag = true;
                logSelect(type, flag, Collections.singletonList(list));
            }
            case "poisson" -> {
                generators = new Generators(params[0], 0.0);
                list = generators.generatePoisson(count);
                flag = true;
                logSelect(type, flag, Collections.singletonList(list));
            }
        }
        extracted(list, fileWriter);
        fileWriter.close();
    }

    private void extracted(List<String> list, FileWriter fileWriter) {
        list.forEach(e -> {
            try {
                fileWriter.append(e).append("\n");
            } catch (IOException ee) {
                System.out.println(ee.getMessage());
                ee.printStackTrace();
            }
        });
    }

    private void logSelect(String value, boolean flag, List<Object> list) {
        if (flag) {
            if (list.size() != 0) {
                System.out.println("Distribution " + value + " successfully generated.");
            } else {
                exceptionHandler.stop("Error generating " + value + " distribution.");
            }
        } else {
            System.out.println("Beginning of " + value + " distribution generation.");
        }
    }
}
