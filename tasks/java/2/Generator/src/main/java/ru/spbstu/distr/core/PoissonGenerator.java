package ru.spbstu.distr.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoissonGenerator extends AbstractGenerator {

    private final double lambda;

    public PoissonGenerator(double lambda) {
        if (lambda > 0)
            this.lambda = lambda;
        else {
            Logger log = LogManager.getLogger(PoissonGenerator.class);
            log.warn("Lambda must be positive. Setting to 1.0 by default");
            this.lambda = 1.0;
        }
    }

    @Override
    public double nextValue() {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            p *= random.nextDouble();
            ++k;
        } while (p > L);

        return k - 1;
    }

    @Override
    public double[] getParams() {
        return new double[]{lambda};
    }
}
