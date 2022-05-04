package ru.spbstu.telematics.writer.json;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.spbstu.telematics.parameters.ResultParameters;
import ru.spbstu.telematics.variables.Variable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONWriterTest {

    private final String[] paramNames = new String[] {"max", "mean", "median", "min"};
    private final String FILE = "src/test/resources/file.json";
    private int SIZE1 = 100;

    @DataProvider(name = "parameters")
    public Object[][] ResultParameters() {
        Variable<Double> first = new Variable<>("first");
        Variable<Double> second = new Variable<>("second");
        for (int i = -100; i < SIZE1; i++) {
            first.add((double) i);
            second.add((double) i + i * i);
        }
        return new Object[][] {
                new Object[] {ResultParameters.getResultParameters(List.of(first)), List.of(first)},
                new Object[] {ResultParameters.getResultParameters(List.of(second)), List.of(second)},
                new Object[] {ResultParameters.getResultParameters(List.of(first, second)), List.of(first, second)}
        };
    }

    @Test(dataProvider = "parameters")
    public void WriterTest(List<ResultParameters> resultParameters, List<Variable<Double>> variables) throws IOException {
        JSONWriter writer = new JSONWriter(FILE);
        writer.write(resultParameters);
        var lines = Files.readAllLines(Path.of(FILE));
        Assert.assertEquals(lines.size(), resultParameters.size() + 1);
        for (int i = 0; i < variables.size(); i++) {
            String line = lines.get(i + 1);
            Assert.assertTrue(line.contains("max"));
            Assert.assertTrue(line.contains("min"));
            Assert.assertTrue(line.contains("mean"));
            Assert.assertTrue(line.contains("median"));
        }
    }

}
