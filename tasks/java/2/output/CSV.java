package output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;

public class CSV
{
    public static void write(String path,  List<Double> numbers) throws Exception
    {
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for(int i = 0; i < numbers.size() - 1; i++) {
            bw.write(numbers.get(i) + ",");
        }
        bw.write(numbers.get(numbers.size() - 1) + "");

        bw.close();
        fw.close();
    }
}
