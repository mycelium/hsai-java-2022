package distributions.impl;

import distributions.Distribution;

import java.util.ArrayList;
import java.util.List;

public class Poisson extends Distribution {
    double lambda;

    public Poisson(double lambda) throws IllegalArgumentException {
        if (lambda > 0 && !Double.isNaN(lambda))  {
            this.lambda = lambda;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Double nextValue() {
        double elem = 0;
        double p = Math.exp(-lambda);
        double r = rnd.nextDouble() - p;
        while (r > 0) {
            elem++;
            p *= lambda / elem;
            r -= p;
        }
        return elem;
    }
}
