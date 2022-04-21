package com.example;

import java.util.Random;

public class NormalDistribution implements Distribution {

    private static final Random random = new Random();

    private final double std;
    private final double mean;

    public NormalDistribution(double std, double mean) {
        this.std = std;
        this.mean = mean;
    }

    @Override
    public double generateNext() {
        return random.nextGaussian() * std + mean;
    }
}
