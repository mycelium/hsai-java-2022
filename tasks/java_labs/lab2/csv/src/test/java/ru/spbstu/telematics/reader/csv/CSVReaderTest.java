package ru.spbstu.telematics.reader.csv;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CSVReaderTest {

    private final String DIR = "src/test/resources/";
    private final String file1 = "file1.csv";
    private final String file2 = "file2.csv";
    private final String[] NAMES = new String[] {"Normal 1, -10", "Poisson 10", "Uniform [-50; -40]"};

    @DataProvider(name = "withNames")
    public Object[][] correctInput() {
        return new Object[][] {
                new Object[] {DIR + file1, 0, 3, NAMES},
                new Object[] {DIR + file2, 30, 3, NAMES}
        };
    }

    @Test(dataProvider = "withNames")
    public void CSVReaderCorrectLinesTest(String file, int values, int samples, String[] names) throws Exception {
        CSVReader reader = new CSVReader(file);
        var list = reader.readAllDistribution();
        Assert.assertEquals(list.size(), samples);
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i).size(), values);
            Assert.assertEquals(list.get(i).getName(), names[i]);
        }
    }
}