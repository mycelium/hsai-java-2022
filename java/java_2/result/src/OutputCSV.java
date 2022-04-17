import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OutputCSV {

    private static Logger log = Logger.getLogger(OutputCSV.class.getName());

    public void outputCSV(int size, ArrayList<Double> list, String Path) throws IOException {
        log.info("Saving data to CSV file");
        File file = new File((Path.matches(".+[/\\\\]") || "".equals(Path) ? Path : Path + "/") + "result.csv");
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("data\n");
        //если будет время: этот процесс лучше сделать многопоточным
        for (int i = 0; i < size; i++)
            csvWriter.append(list.get(i) + "\n");
        log.info("Data saved to CSV file");
        System.out.println("See results in " + Path + "\\" + "result.csv");
        csvWriter.flush();
        csvWriter.close();
    }

}
