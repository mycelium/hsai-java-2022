package org.spbstu;

public class PoissonDistribution extends Distribution {

    private final double L;

    public PoissonDistribution(double mean) { this.L = Math.exp(-mean); }

    @Override
    public double next() {
        int k = 0;
        double p = 1.0;
        do {
            p = p * random.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

}
