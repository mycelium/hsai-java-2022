package com.polytech.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

public class NormalDistribution implements Distribution{

    private static final Random rnd = new Random();
    private static final Logger logger = new ConsoleLogger(NormalDistribution.class.getName());

    private final double std;
    private final double mean;

    public NormalDistribution(double std, double mean) {
        this.std = std;
        this.mean = mean;
    }

    @Override
    public List<Double> generate(int n) {
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            data.add(generateOne());
        }

        logger.trace("generated " + n + " values");
        return data;
    }

    @Override
    public double generateOne() {
        return rnd.nextGaussian() * std + mean;
    }
}
