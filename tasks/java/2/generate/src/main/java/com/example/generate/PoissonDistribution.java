package com.example.generate;

public class PoissonDistribution extends Distribution {
    private final double alpha;

    public PoissonDistribution(double alpha) {
        this.alpha = alpha;
    }

    public double getPoint() {
        double L = Math.exp(-alpha);
        int k = 0;
        double p = 1.0;
        do {
            p = p * getRandom().nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
