import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerationWritingProcess {

    public void DoGenerationWriting(String fileAbsolute, long count, Double[] param, String type) {
        Path path = Paths.get(fileAbsolute);
        File file = new File(path + File.separator + "java2result.csv");
        if (file.exists()) {
            file.delete();
            System.out.println("The deletion of the old file completed successfully!");
        }
        try {
            file.createNewFile();
            System.out.println("The creation of the output file completed successfully!");
            FileWriter filewriter = new FileWriter(file);
            generateData(count, param, type, filewriter);
            System.out.println("The file generated successfully to: " + Main.green + file.getAbsolutePath() + Main.reset);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateData(long count, Double[] param, String type, FileWriter filewriter) throws IOException {
        DistributionGenerators distrbgenert;
        switch (type) {
            case "normal" -> {
                distrbgenert = new DistributionGenerators(param[0], param[1]);
                System.out.println("The normal distribution generation is started...");
                List<Double> valueslist = distrbgenert.NormalDistribution(count);
                if (valueslist.size() == count) {
                    System.out.println("The normal distribution generation completed successfully!");
                } else {
                    System.out.println(Main.red + "The normal distribution generation failed with error!" + Main.reset);
                    System.exit(-1);
                }
                valueslist.forEach(exc -> {
                    try {
                        filewriter.append(exc.toString()).append("\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "uniform" -> {
                distrbgenert = new DistributionGenerators(param[0], param[1]);
                System.out.println("The uniform distribution generation is started...");
                List<Double> valueslist = distrbgenert.UniformDistribution(count);
                if (valueslist.size() == count) {
                    System.out.println("The uniform distribution generation completed successfully!");
                } else {
                    System.out.println(Main.red + "The uniform distribution generation failed with error!" + Main.reset);
                    System.exit(-1);
                }
                valueslist.forEach(exc -> {
                    try {
                        filewriter.append(exc.toString()).append("\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "poisson" -> {
                distrbgenert = new DistributionGenerators(param[0], 0.0);
                System.out.println("The poisson distribution generation is started...");
                List<Integer> valueslist = distrbgenert.PoissonDistribution(count);
                if (valueslist.size() == count) {
                    System.out.println("The poisson distribution generation completed successfully!");
                } else {
                    System.out.println(Main.red + "The poisson distribution generation failed with error!" + Main.reset);
                    System.exit(-1);
                }
                valueslist.forEach(exc -> {
                    try {
                        filewriter.append(exc.toString()).append("\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }
        filewriter.close();
    }
}
