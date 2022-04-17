package distributions;
import java.util.List;
import java.util.ArrayList;

public class UniformDistribution extends Distribution {
    double xAxisMin = 2, xAxisMax = 2.5;

    public UniformDistribution(int numberOfValues, double min, double max) throws Exception {
        super(numberOfValues);
        if (min < max) {
            this.xAxisMin = min;
            this.xAxisMax = max;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public List<Double> generateNumbers(int numberOfValues) {
        List<Double> list = new ArrayList<>(numberOfValues);

        for (int i = 0; i < numberOfValues; i++) {
            list.add(xAxisMin + rnd.nextDouble() * (xAxisMax - xAxisMin));
        }
        return list;
    }
}