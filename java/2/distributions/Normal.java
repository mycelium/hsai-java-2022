package distributions;

import java.util.ArrayList;
import java.util.List;

public class Normal extends Distribution {
    double variance;
    double mean;

    public Normal(int size, double variance, double mean) throws Exception {
        super(size);
        if (variance > 0 && !Double.isNaN(variance) && !Double.isNaN(mean)) {
            this.variance = variance;
            this.mean = mean;
        } else throw new IllegalArgumentException();
    }

    public List<Double> genNumbers(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(rnd.nextGaussian() * variance + mean);
        }
        return list;
    }
}
