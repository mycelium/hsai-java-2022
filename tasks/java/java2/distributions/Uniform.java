package distributions;
import java.util.List;
import java.util.ArrayList;

public class Uniform extends Distribution {
    double min;
    double max;

    public Uniform(int numberOfValues, double min, double max) throws Exception {
        super(numberOfValues);
        if (min < max && !Double.isNaN(min) && !Double.isNaN(max)) {
            this.min = min;
            this.max = max;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public List<Double> genNumbers(int numberOfValues) {
        List<Double> list = new ArrayList<>(numberOfValues);
        for (int i = 0; i < numberOfValues; i++) {
            list.add(min + rnd.nextDouble() * (max - min));
        }
        return list;
    }
}
