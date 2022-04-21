package org.spbstu;

public class NormalDistribution extends Distribution {

    private final double std;
    private final double mean;

    public NormalDistribution(double std, double mean) {
        this.std = std;
        this.mean = mean;
    }

    @Override
    public double next() { return random.nextGaussian() * std + mean; }

}
