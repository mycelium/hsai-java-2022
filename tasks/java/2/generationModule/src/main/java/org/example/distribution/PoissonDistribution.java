package org.example.distribution;

public class PoissonDistribution extends Distribution {
    private final double mean;

    public PoissonDistribution(double mean) {
        this.mean = mean;
    }

    public double getPoint() {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * getRandom().nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
