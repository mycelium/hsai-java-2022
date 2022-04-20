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
        String fileName = "output.csv";
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            file.delete();
            Log.log("Success delete old file.", true);
        }
        try {
            file.createNewFile();
            Log.log("Success create output file.", true);
            FileWriter fw = new FileWriter(file);
            generateData(count, params, type, fw);
            Log.log("Success generated file to: " + file.getAbsolutePath(), true);
        } catch (IOException e) {
            Log.log(e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private void generateData(int count, Double[] params, String type, FileWriter fileWriter) throws IOException {
        Generators generators;
        switch (type) {
            case "normal" -> {
                generators = new Generators(params[0], params[1]);
                Log.log("Beginning of normal distribution generation.", true);
                List<Double> list = generators.generateNormal(count);
                if (list.size() == count) {
                    Log.log("Normal distribution successfully generated.", true);
                } else {
                    Log.log("Error generating normal distribution.", false);
                    throw new InvalidObjectException("Error generating normal distribution.");
                }
                list.forEach(e -> {
                    try {
                        fileWriter.append(e.toString()).append("\n");
                    } catch (IOException ee) {
                        Log.log(ee.getMessage(), false);
                        ee.printStackTrace();
                    }
                });
            }
            case "uniform" -> {
                generators = new Generators(params[0], params[1]);
                Log.log("Beginning of uniform distribution generation.", true);
                List<Double> list = generators.generateNormal(count);
                if (list.size() == count) {
                    Log.log("Uniform distribution successfully generated.", true);
                } else {
                    Log.log("Error generating uniform distribution.", false);
                    throw new InvalidObjectException("Error generating normal distribution.");
                }
                list.forEach(e -> {
                    try {
                        fileWriter.append(e.toString()).append("\n");
                    } catch (IOException ee) {
                        Log.log(ee.getMessage(), false);
                        ee.printStackTrace();
                    }
                });
            }
            case "poisson" -> {
                generators = new Generators(params[0], 0.0);
                Log.log("Beginning of poisson distribution generation.", true);
                List<Integer> list = generators.generatePoisson(count);
                if (list.size() == count) {
                    Log.log("Poisson distribution successfully generated.", true);
                } else {
                    Log.log("Error generating poisson distribution.", false);
                    throw new InvalidObjectException("Error generating poisson distribution.");
                }
                list.forEach(e -> {
                    try {
                        fileWriter.append(e.toString()).append("\n");
                    } catch (IOException ee) {
                        Log.log(ee.getMessage(), false);
                        ee.printStackTrace();
                    }
                });
            }
        }
        fileWriter.close();
    }
}
