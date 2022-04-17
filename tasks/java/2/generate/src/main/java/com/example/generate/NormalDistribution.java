package com.example.generate;

public class NormalDistribution extends Distribution {
    private final double mean, stddev;

    public NormalDistribution(double mean, double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }

    public double getPoint() {
        return getRandom().nextGaussian() * stddev + mean;
    }
}
