package output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSV
{
    public static void write(String path,  List<Double> numbers) throws Exception
    {
        File file = new File(path);

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        String distributionLine = IntStream.range(0, numbers.size())
                .mapToObj(i -> numbers.get(i) + "")
                .collect(Collectors.joining(","));

        bw.write(distributionLine);
        bw.close();
        fw.close();
    }
}
