package org.example.log;

import java.util.Date;

public class Log {
    private final String className;

    public Log(Class clazz) {
        this.className = clazz.getName();
    }

    public void info(String... params) {
        System.out.println(
                new Date() + " " +
                className + " " +
                String.join(" ", params)
        );
    }

    public void error(String... params) {
        System.err.println(
            new Date() + " " +
                className + " " +
                String.join(" ", params)
        );
    }
}
