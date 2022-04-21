package com.example;

import java.util.Random;

public class UniformDistribution implements Distribution {

    private static final Random random = new Random();

    private final double min;
    private final double max;

    public UniformDistribution(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double generateNext() {
        return random.nextDouble() * (max - min) + min;
    }
}
