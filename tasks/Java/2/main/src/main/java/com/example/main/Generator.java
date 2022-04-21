package com.example.main;

import com.example.generate.Distribution;
import com.example.generate.NormalDistribution;
import com.example.generate.PoissonDistribution;
import com.example.generate.UniformDistribution;
import com.example.io.CsvWriter;
import com.example.io.OutputWriter;
import com.example.io.SqliteWriter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Generator {
    private final static Scanner sc = new Scanner(System.in);

    public static String run() {
        Distribution dist = promptDistribution();
        int samples = promptSampleSize();
        OutputWriter ow = promptWriter();

        List<Double> series = new LinkedList<>();
        for (int i = 0; i < samples; i++) {
            series.add(dist.getPoint());
        }

        ow.write(series);
        return ow.getOutputFilePath().toString();
    }

    private static Distribution promptDistribution() {
        System.out.println("Choose distribution:\n" +
                "'uniform [leftBound] [rightBound]'\n" +
                "'normal [mean] [stddev]'\n" +
                "'poisson [alpha]'");

        String[] distArgs = sc.nextLine().split(" ");
        Distribution distribution = null;

        try {
            distribution = constructDistribution(distArgs);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Distribution arguments are not numbers");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal set of args for distribution");
        }

        return distribution;
    }

    private static int promptSampleSize() {
        System.out.println("How many numbers shall we write?");
        return Integer.parseInt(sc.nextLine());

    }

    private static OutputWriter promptWriter() {
        System.out.println("Choose output type and directory:\n" +
                "'csv [path_to_directory]'\n" +
                "'db [path_to_directory]'");

        String[] outArgs = sc.nextLine().split(" ");

        OutputWriter ow;
        try {
            ow = constructWriter(outArgs);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ow;
    }

    private static Distribution constructDistribution(String[] args) {
        switch(args[0]) {
            case "uniform":
                return new UniformDistribution(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            case "normal":
                return new NormalDistribution(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            case "poisson":
                return new PoissonDistribution(Double.parseDouble(args[1]));
            default:
                throw new IllegalArgumentException("Distribution type " + args[0] + " not supported");
        }
    }

    private static OutputWriter constructWriter(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }

        switch(args[0]) {
            case "csv":
                return new CsvWriter(args[1]);
            case "db":
                return new SqliteWriter(args[1]);
            default:
                throw new IllegalArgumentException("Writer type " + args[1] + " not supported");
        }
    }
}
