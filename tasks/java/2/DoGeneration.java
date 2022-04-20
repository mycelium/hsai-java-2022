import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DoGeneration {

    public void doGeneration(String filePath, int cnt, Double[] params, String type) {
        Path path = Paths.get(filePath);
        File file = new File(path + File.separator + "generatedData.csv");
        if (file.exists()) {
            file.delete();
            System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Deleted old file.");
        }
        try {
            file.createNewFile();
            System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Created output file.");
            FileWriter fw = new FileWriter(file);
            generate(cnt, params, type, fw);
            System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Data generated to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + e.getMessage());
            System.exit(-1);
        }
    }

    private void generate(int cnt, Double[] params, String type, FileWriter fw) throws IOException {
        DataGeneration generators;
        switch (type) {
            case "uniform" -> {
                generators = new DataGeneration(params[0], params[1]);
                System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Uniform distribution generation started.");
                List<Double> valuesList = generators.generateUniform(cnt);
                if (valuesList.size() == cnt) {
                    System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Uniform distribution generated successfully.");
                } else {
                    System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Error generating uniform distribution.");
                    System.exit(-1);
                }
                valuesList.forEach(e -> {
                    try {
                        fw.append(e.toString()).append("\n");
                    } catch (IOException exc) {
                        System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + exc.getMessage());
                        System.exit(-1);
                    }
                });
            }
            case "normal" -> {
                generators = new DataGeneration(params[0], params[1]);
                System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor +  "Normal distribution generation started.");
                List<Double> valuesList = generators.generateNormal(cnt);
                if (valuesList.size() == cnt) {
                    System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Normal distribution generated successfully.");
                } else {
                    System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Error generating normal distribution.");
                    System.exit(-1);
                }
                valuesList.forEach(e -> {
                    try {
                        fw.append(e.toString()).append("\n");
                    } catch (IOException exc) {
                        System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + exc.getMessage());
                        System.exit(-1);
                    }
                });
            }
            case "poisson" -> {
                generators = new DataGeneration(params[0], 0.0);
                System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Poisson distribution generation started.");
                List<Integer> valuesList = generators.generatePoisson(cnt);
                if (valuesList.size() == cnt) {
                    System.out.println(Main.infoColor + "INFORMATION: " + Main.resetColor + "Poisson distribution generated successfully.");
                } else {
                    System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + "Error generating poisson distribution.");
                    System.exit(-1);
                }
                valuesList.forEach(e -> {
                    try {
                        fw.append(e.toString()).append("\n");
                    } catch (IOException exc) {
                        System.out.println(Main.errorColor + "ERROR: " + Main.resetColor + exc.getMessage());
                        System.exit(-1);
                    }
                });
            }
        }
        fw.close();
    }
}
