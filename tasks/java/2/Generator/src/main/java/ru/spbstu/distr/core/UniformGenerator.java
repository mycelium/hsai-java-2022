package ru.spbstu.distr.core;

public class UniformGenerator extends AbstractGenerator {

    private final double a;
    private final double b;

    public UniformGenerator(double left, double right) {
        if (left <= right) {
            a = left;
            b = right;
        } else {
            a = right;
            b = left;
        }
    }

    @Override
    public double nextValue() {
        return random.nextDouble() * (b - a) + a;
    }

    @Override
    public double[] getParams() {
        return new double[]{a, b};
    }
}
