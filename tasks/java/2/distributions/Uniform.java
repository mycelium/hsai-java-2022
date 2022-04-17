package distributions;

import java.util.ArrayList;
import java.util.List;

public class Uniform extends Distribution {
    double min;
    double max;

    public Uniform(int size, double min, double max) throws Exception {
        super(size);
        if (min < max && !Double.isNaN(min) && !Double.isNaN(max)) {
            this.min = min;
            this.max = max;
        } else throw new IllegalArgumentException();
    }

    public List<Double> genNumbers(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(min + rnd.nextDouble() * (max - min));
        }
        return list;
    }
}
