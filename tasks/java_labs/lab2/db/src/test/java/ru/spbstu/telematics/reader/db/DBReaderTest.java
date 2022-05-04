package ru.spbstu.telematics.reader.db;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DBReaderTest {

    private final String DIR = "src/test/resources/";
    private final String file1 = "file1.db";
    private final String file2 = "file2.db";
    private final String[] NAMES = new String[] {"Normal_0", "Poisson_1", "Uniform_2"};
    private final String TABLE_NAME = "Table1";

    @DataProvider(name = "withNames")
    public Object[][] correctInput() {
        return new Object[][] {
                new Object[] {DIR + file1, 0, 3, NAMES, TABLE_NAME},
                new Object[] {DIR + file2, 30, 3, NAMES, TABLE_NAME}
        };
    }

    @Test(dataProvider = "withNames")
    public void DBReaderCorrectLinesTest(String file, int values, int samples, String[] names, String table) throws Exception {
        DBReader reader = new DBReader(file, table);
        var list = reader.readAllDistribution();
        Assert.assertEquals(list.size(), samples);
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i).size(), values);
            Assert.assertEquals(list.get(i).getName(), names[i]);
        }
    }
}
