package distributions;
import java.util.List;
import java.util.ArrayList;

public class PoissonDistribution extends Distribution {
    double lambda = 0.5;

    public PoissonDistribution(int numberOfValues, double lambda) throws Exception {
        super(numberOfValues);
        if (lambda > 0)  {
            this.lambda = lambda;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private double generatePoissonNumbers() {
        int value = 0;
        double p = Math.exp(-lambda);
        double r = rnd.nextDouble() - p;

        while (r > 0) {
            value++;
            p *= lambda / value;
            r -= p;
        }
        return value;
    }

    public List<Double> generateNumbers(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(generatePoissonNumbers());
        }
        return list;
    }
}