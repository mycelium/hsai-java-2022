package org.example.distribution;

public class NormalDistribution extends Distribution {
    private final double scale, shift;

    public NormalDistribution(double shift, double scale) {
        this.scale = shift;
        this.shift = scale;
    }

    public double getPoint() {
        return getRandom().nextGaussian() * shift + scale;
    }
}