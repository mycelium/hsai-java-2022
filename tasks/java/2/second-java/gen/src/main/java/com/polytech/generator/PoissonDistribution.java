package com.polytech.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

public class PoissonDistribution implements Distribution{

    private static final Random rnd = new Random();
    private static final Logger logger = new ConsoleLogger(PoissonDistribution.class.getName());

    private final double mean;

    public PoissonDistribution(double mean) {
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
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * rnd.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
