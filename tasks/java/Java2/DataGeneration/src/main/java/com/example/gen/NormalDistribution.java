package com.example.gen;

public class NormalDistribution extends Distribution {
    private final double firstParam;
    private final double secondParam;

    public NormalDistribution(double firstParam, double secondParam) {
        this.firstParam = firstParam;
        this.secondParam = secondParam;
    }

    public double nextPoint() {
        double point = random.nextGaussian() * secondParam + firstParam;
        return point;
    }
}