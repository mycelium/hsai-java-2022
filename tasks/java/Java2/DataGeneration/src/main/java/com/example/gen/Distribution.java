package com.example.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public abstract class Distribution {
    private static Logger logger = Logger.getLogger(Distribution.class.getName());;

    protected final Random random = new Random();
    public abstract double nextPoint();

    public List<Double> getListOfPoints(int n){
        List<Double> distribution = new ArrayList<>();

        logger.info("Start distribution");
        for(int i = 0; i < n; i++) {
            distribution.add(nextPoint());
        }
        logger.info("Finish distribution");
        return distribution;
    }
}