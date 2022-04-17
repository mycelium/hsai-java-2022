package distributions;
import java.util.List;
import java.util.ArrayList;

public class Normal extends Distribution {
    double variance;
    double mean;

    public Normal(int numberOfValues, double variance, double mean) throws Exception {
        super(numberOfValues);
        if (variance > 0 && !Double.isNaN(variance) && !Double.isNaN(mean)) {
            this.variance = variance;
            this.mean = mean;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public List<Double> genNumbers(int numberOfValues) {
        List<Double> list = new ArrayList<>(numberOfValues);
        for (int i = 0; i < numberOfValues; i++) {
            list.add(rnd.nextGaussian() * variance + mean);
        }
        return list;
    }
}
