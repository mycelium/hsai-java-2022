package com.example.gen;

public class UniformDistribution extends Distribution {
    private final double min;
    private final double max;

    public UniformDistribution(double min, double max) {
        this.min = min;
        this.max = max;
    }
    public double nextPoint() {
        double point = min + (max - min) * random.nextDouble();
        return point;
    }
}