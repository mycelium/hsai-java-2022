package com.polytech.log;

public interface Logger {
    void trace(String message);
    void error(String message);
    void error(Exception e);
}
