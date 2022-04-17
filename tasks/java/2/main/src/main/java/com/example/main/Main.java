package com.example.main;

import com.example.generate.Distribution;
import com.example.generate.NormalDistribution;
import com.example.generate.PoissonDistribution;
import com.example.generate.UniformDistribution;
import com.example.io.CsvWriter;
import com.example.io.OutputWriter;
import com.example.io.SqliteWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        OutputWriter ow = new SqliteWriter("resources/database");
        OutputWriter ow = new CsvWriter("resources/rand.csv");
        Distribution dis = new NormalDistribution(5, 3);
//        Distribution dis = new UniformDistribution(0,10);

        List<Double> series = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            series.add(dis.getPoint());
        }

        ow.write(series);
    }
}
