package output;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVOutput
{
    public static void writeToCsv(String path,  List<Double> elements) throws Exception
    {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);

        String res = IntStream.range(0, elements.size()).mapToObj(i -> elements.get(i) + "")
                        .collect(Collectors.joining(",\n"));

        fileWriter.write(res);
        fileWriter.close();
    }
}
