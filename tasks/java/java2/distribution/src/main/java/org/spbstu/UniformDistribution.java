package org.spbstu;

public class UniformDistribution extends Distribution {

    private final double min;
    private final double max;

    public UniformDistribution(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double next() { return random.nextDouble() * (max - min) + min; }

}
