package distributions;
import java.util.List;
import java.util.ArrayList;

public class Poisson extends Distribution {
    double lambda;

    public Poisson(int numberOfValues, double lambda) throws Exception {
        super(numberOfValues);
        if (lambda > 0 && !Double.isNaN(lambda))  {
            this.lambda = lambda;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public double genPoissonNum() {
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

    public List<Double> genNumbers(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(genPoissonNum());
        }
        return list;
    }
}
