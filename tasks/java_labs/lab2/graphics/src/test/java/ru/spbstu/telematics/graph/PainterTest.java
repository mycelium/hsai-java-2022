package ru.spbstu.telematics.graph;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.spbstu.telematics.variables.Variable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class PainterTest {

    private final String DIR = "src/test/resources";
    private int SIZE1 = 100;

    @DataProvider(name = "parameters")
    public Object[][] ResultParameters() {
        Variable<Double> first = new Variable<>("first");
        Variable<Double> second = new Variable<>("second");
        for (int i = -100; i < SIZE1; i++) {
            first.add((double) i); //равномерное
            second.add((double) i + i * i);//убывающая, прикольная
        }
        return new Object[][] {
                new Object[] {List.of(first, second)},
                new Object[] {List.of(first)},
                new Object[] {List.of(second)}
        };
    }

    private void clear() throws IOException {
        Path path = Path.of(DIR);
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        Files.createDirectories(path);
    }

    @Test(dataProvider = "parameters")
    public void PainterTest(List<Variable<Double>> variables) throws IOException {
        try {
            clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Painter painter = new Painter(variables);
        painter.plotHists(DIR);
        Assert.assertEquals(Files.list(Path.of(DIR)).count(), variables.size());
    }

}