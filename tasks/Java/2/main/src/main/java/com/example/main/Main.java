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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Success. Path to file: " + Generator.run());
        } catch (IllegalArgumentException e) {
            System.out.println("Unsuccessful: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Unsuccessful");
            e.printStackTrace();
        }

    }
}
