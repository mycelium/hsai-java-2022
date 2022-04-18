package org.example.distribution;

public class UniformDistribution extends Distribution {

    private final double lower;
    private final double upper;

    public UniformDistribution(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public double getPoint() {
        return getRandom().nextDouble() * (upper - lower) + lower;
    }
}