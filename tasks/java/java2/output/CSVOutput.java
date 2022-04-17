package output;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;

public class CSVOutput
{
    public static void writeToCsv(String path,  List<Double> numbers) throws Exception
    {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);

        for(int i = 0; i < numbers.size() - 1; i++) {
            bufWriter.write(numbers.get(i) + ",");
        }
        bufWriter.write(numbers.get(numbers.size() - 1) + "");

        bufWriter.close();
        fileWriter.close();
    }
}
