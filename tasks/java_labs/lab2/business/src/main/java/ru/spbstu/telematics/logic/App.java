package ru.spbstu.telematics.logic;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import ru.spbstu.telematics.analyzer.normality.NormalityAnalyzer;
import ru.spbstu.telematics.graph.Painter;
import ru.spbstu.telematics.parameters.ResultParameters;
import ru.spbstu.telematics.reader.csv.CSVReader;
import ru.spbstu.telematics.reader.db.DBReader;
import ru.spbstu.telematics.variables.Variable;
import ru.spbstu.telematics.writer.json.JSONWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class App {

    @Parameter(names = { "-csv" }, description = "Input csv file")
    private String csv;

    @Parameter(names = "-db", description = "Input db file")
    private String db;

    @Parameter(names = "-table", description = "Input table in db")
    private String table;

    @Parameter(names = "-n", description = "Normality check")
    private boolean n = false;

    @Parameter(names = "-img", description = "Image output")
    private String img;

    @Parameter(names = "-json", description = "JSON output")
    private String json;

    /**
     * -csv -db file[.csv|.db] -table table_name -img dir -json file.json -n
     */

    public static void main(String[] args) {
        App main = new App();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        try {
            main.checkInput();
            main.work();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void checkInput() {
        if (db != null && csv != null) {
            throw new IllegalArgumentException("Two input files!");
        }
        if (db == null && csv == null) {
            throw new IllegalArgumentException("Zero input files!");
        }
        if (db != null) {
            if (table == null) {
                throw new IllegalArgumentException("Table name for table in db not found");
            }
        }
    }

    private void work() throws SQLException, IOException {
        List<Variable<Double>> sample;
        if (db != null) {
            DBReader reader = new DBReader(db, table);
            sample = reader.readAllDistribution();
        } else {
            CSVReader reader = new CSVReader(csv);
            sample = reader.readAllDistribution();
        }
        if (n) {
            var isBooleanList = NormalityAnalyzer.isNormal(sample);
            for (int i = 0; i < isBooleanList.size(); i++) {
                String resultForEach = (isBooleanList.get(i) ? " is " : " isn't ") + "normal distributed";
                System.out.println(i + " " + sample.get(i).getName() + resultForEach);
            }
        }
        var resultParametersList = ResultParameters.getResultParameters(sample);
        if (json != null) {
            JSONWriter writer = new JSONWriter(json);
            writer.write(resultParametersList);
        }
        if (img != null) {
            Painter painter = new Painter(sample);
            painter.plotHists(img);
        }
    }

}
