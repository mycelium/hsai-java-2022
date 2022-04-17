package distributions;

import java.util.ArrayList;
import java.util.List;

public class Poisson extends Distribution {
    double lambda;

    public Poisson(int size, double lambda) throws Exception {
        super(size);
        if (lambda > 0 && !Double.isNaN(lambda))  {
            this.lambda = lambda;
        } else throw new IllegalArgumentException();
    }

    public double genPoissonNum() {
        int elem = 0;
        double p = Math.exp(-lambda);
        double r = rnd.nextDouble() - p;
        while (r > 0) {
            elem++;
            p *= lambda / elem;
            r -= p;
        }
        return elem;
    }

    public List<Double> genNumbers(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(genPoissonNum());
        }
        return list;
    }
}
