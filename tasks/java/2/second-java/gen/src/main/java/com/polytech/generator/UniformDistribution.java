package com.polytech.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

public class UniformDistribution implements Distribution{

    private static final Random rnd = new Random();
    private static final Logger logger = new ConsoleLogger(UniformDistribution .class.getName());

    private final double min;
    private final double max;

    public UniformDistribution(double std, double mean) {
        this.min = std;
        this.max = mean;
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
        return rnd.nextDouble() * (max - min) + min;
    }
}
