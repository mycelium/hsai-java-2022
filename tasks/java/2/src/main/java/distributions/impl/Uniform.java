package distributions.impl;

import distributions.Distribution;

import java.util.ArrayList;
import java.util.List;

public class Uniform extends Distribution {
    double min;
    double max;

    public Uniform(double min, double max) throws IllegalArgumentException {
        if (min < max && !Double.isNaN(min) && !Double.isNaN(max)) {
            this.min = min;
            this.max = max;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Double nextValue() {
        return min + rnd.nextDouble() * (max - min);
    }
}
