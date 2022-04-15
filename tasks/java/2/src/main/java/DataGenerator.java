import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataGenerator {

    public static File getPopulatedFile() {
        final String path = (String) Main.params.get("out");
        File file = new File((path.matches(".+[/\\\\]") || "".equals(path) ? path : path + "/") + "result.csv");
        try {
            final AtomicBoolean running = new AtomicBoolean(false);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            Thread progress = new Thread(() -> {
                running.set(true);
                while (running.get()) {
                    System.out.print("*");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.print("Work in progress ");
            progress.start();
            fw.append(getData());
            fw.close();
            Thread.sleep(200);
            running.set(false);
            System.out.println("\nSuccess!");
        } catch (IOException IOE) {
            System.out.println("Something went wrong while managing the output file! It seems you lack of privileges!");
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static String getData() {
        Random rnd = new Random();
        final StringBuilder data = new StringBuilder();
        final Distribution dist = (Distribution) Main.params.get("type");
        final Float[] pars = (Float[]) Main.params.get("params");

        for (long  i = 0; i < (long) Main.params.get("cnt"); i++) {
            data.append(dist.getEvaluation().apply(pars, rnd)).append("\n");
        }
        return data.toString();
    }
}
