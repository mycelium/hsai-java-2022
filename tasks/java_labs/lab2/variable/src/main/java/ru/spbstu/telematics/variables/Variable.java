package ru.spbstu.telematics.variables;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Variable<T> extends ArrayList<T> {

    private static Logger logger = LogManager.getLogger(Variable.class);

    private String name;

    public Variable(String name) {
        logger.info("Create variable" + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

