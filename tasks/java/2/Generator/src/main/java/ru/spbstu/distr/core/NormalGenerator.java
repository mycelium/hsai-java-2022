package ru.spbstu.distr.core;

public class NormalGenerator extends AbstractGenerator {

    private final double mx;
    private final double s;

    public NormalGenerator(double mx, double s) {
        this.mx = mx;
        this.s = (s > 0) ? s : 1.0;
    }

    @Override
    public double nextValue() {
        return random.nextGaussian() * s + mx;
    }

    @Override
    public double[] getParams() {
        return new double[]{mx, s};
    }
}
