package org.example.distribution;

import org.example.io.DBWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public abstract class Distribution {
    private static Logger logger = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(DBWriter.class.getName());
    }
    private final Random random = new Random();
    protected Random getRandom() {
        return random;
    }
    public abstract double getPoint();

    public List<Double> getPoints(int n){
        List<Double> distribution = new ArrayList<>();

        logger.info("Start generating data");
        for(int i = 0; i < n; i++) {
            distribution.add(getPoint());
        }
        logger.info("Finish generating data");
        return distribution;
    }
}