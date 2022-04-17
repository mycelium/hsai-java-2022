package com.example.generate;

public class UniformDistribution extends Distribution {
    private final double a, b;

    public UniformDistribution(double leftBound, double rightBound) {
        a = leftBound;
        b = rightBound;
    }

    public double getPoint() {
        return a + (b - a) * getRandom().nextDouble();
    }
}
