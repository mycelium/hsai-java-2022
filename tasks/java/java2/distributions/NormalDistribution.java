package distributions;
import java.util.List;
import java.util.ArrayList;

public class NormalDistribution extends Distribution {
    double variance = 2, mean = 1;

    public NormalDistribution(int numberOfValues, double variance, double mean) throws Exception {
        super(numberOfValues);
        if (variance > 0) {
            this.variance = variance;
            this.mean = mean;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public List<Double> generateNumbers(int numberOfValues) {
        List<Double> list = new ArrayList<>(numberOfValues);
        for (int i = 0; i < numberOfValues; i++) {
            list.add(rnd.nextGaussian() * variance + mean);
        }
        return list;
    }
}