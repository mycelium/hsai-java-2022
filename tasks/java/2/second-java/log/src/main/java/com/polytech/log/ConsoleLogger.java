package com.polytech.log;

public record ConsoleLogger(String className) implements Logger {

    public void trace(String message) {
        System.out.println(className + " " + message);
    }

    public void error(String message) {
        System.err.println(className + " " + message);
    }

    public void error(Exception e) {
        System.err.println(className + " error: " + e.getMessage());
    }
}
