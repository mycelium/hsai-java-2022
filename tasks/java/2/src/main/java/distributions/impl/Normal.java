package distributions.impl;

import distributions.Distribution;

import java.util.ArrayList;
import java.util.List;

public class Normal extends Distribution {
    double variance;
    double mean;

    public Normal(double variance, double mean) throws IllegalArgumentException {
        if (variance > 0 && !Double.isNaN(variance) && !Double.isNaN(mean)) {
            this.variance = variance;
            this.mean = mean;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Double nextValue() {
        return rnd.nextGaussian() * variance + mean;
    }
}
