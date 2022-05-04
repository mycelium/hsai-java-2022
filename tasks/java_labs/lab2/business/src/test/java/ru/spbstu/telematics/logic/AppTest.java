package ru.spbstu.telematics.logic;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppTest {

    private static final String DIR = "src/test/resources/";
    private static final String DB = DIR + "input/outDB.db";
    private static final String CSV = DIR + "input/outCSV.csv";
    private static final String IMG = DIR + "output/img";
    private static final String JSON = DIR + "output/json";
    private static final String JSON1 = DIR + "output/json/file1.json";
    private static final String JSON2 = DIR + "output/json/file2.json";

    @Test
    public static void MainTest() throws IOException {
        reCreate();
        App.main(new String[] {"-csv", CSV, "-n"});
        App.main(new String[] {"-csv", CSV, "-n", "-json", JSON1});
        App.main(new String[] {"-db", DB, "-table", "Table1", "-n", "-json", JSON2});
        App.main(new String[] {"-csv", CSV, "-img", IMG});
    }

    private static void reCreate() throws IOException {
        Files.createDirectories(Path.of(DIR));
        Files.createDirectories(Path.of(IMG));
        Files.createDirectories(Path.of(JSON));
    }

}
