package com.example;

import java.util.Random;

public class PoissonDistribution implements Distribution {

    private static final Random random = new Random();

    private final double mean;

    public PoissonDistribution(double mean) {
        this.mean = mean;
    }

    @Override
    public double generateNext() {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * random.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
