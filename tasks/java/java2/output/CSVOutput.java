package output;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;

public class CSVOutput
{
    public static void writeToCsv(String path,  List<Double> elements) throws Exception
    {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

        for(int i = 0; i < elements.size(); i++) {
            if ( i != elements.size()-1) {
                bufferWriter.write(elements.get(i) + ",");
            }
            else {
                bufferWriter.write(elements.get(elements.size() - 1) + "");
            }
        }

        bufferWriter.close();
        fileWriter.close();
    }
}
