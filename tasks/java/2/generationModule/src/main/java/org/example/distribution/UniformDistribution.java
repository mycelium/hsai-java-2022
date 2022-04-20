package org.example.distribution;

public class UniformDistribution extends Distribution {
    private final double lowerBound, upperBound;

    public UniformDistribution(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public double getPoint() {
        return lowerBound + (upperBound - lowerBound) * getRandom().nextDouble();
    }
}