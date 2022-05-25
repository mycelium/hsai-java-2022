package csv;

import distributions.Distribution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Csv
{
    public static void write(String path, Distribution distribution, Integer size)
    {
        File file = new File(path);

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

//            String distributionLine = numbers.stream()
//                    .map(number -> number + "")
//                    .collect(Collectors.joining(","));

            for (int i = 0; i < size; i++) {
                bw.write(String.format("%f,", distribution.nextValue()));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
