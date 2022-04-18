package org.example.distribution;

public class NormalDistribution extends Distribution {

    private final double scale;
    private final double shift;

    public NormalDistribution(double scale, double shift) {
        this.scale = scale;
        this.shift = shift;
    }

    public double getPoint() {
        return getRandom().nextGaussian() * scale + shift;
    }
}
