package com.example.gen;

public class PoissonDistribution extends Distribution {
    private final double m;

    public PoissonDistribution(double m) {

        this.m = m;
    }

    public double nextPoint() {
        double L = Math.exp(-m);
        int k = 0;
        double p = 1.0;
        do {
            p = p * random.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
