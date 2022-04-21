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

    public NormalDistribution getWithArguments(double[] args) {
        if (args.length != 2 || args[0] > args[1]) {
            throw new IllegalArgumentException();
        }

        return new NormalDistribution(args[0], args[1]);
    }
}
