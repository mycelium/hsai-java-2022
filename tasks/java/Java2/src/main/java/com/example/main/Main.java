package com.example.main;

public class Main {
    public static void main(String[] args) {
        String[] split = "2 5 -d normal -n 15000 -o csv -p .\\src\\main\\resources".split(" ");
        int exitCode = new  picocli.CommandLine(new CommandLineInterface()).execute(split);
    }
}

