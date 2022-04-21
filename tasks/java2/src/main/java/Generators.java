import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generators {
    private double std;
    private double mean;
    private Random random = new Random();

    Generators(double std, double mean) {
        this.std = std;
        this.mean = mean;
    }

    public List<Double> generateNormal(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(random.nextGaussian() * mean + std);
        }
        return list;
    }

    public List<Double> generatePoisson(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generateNextPoisson());
        }
        return list;
    }

    private double generateNextPoisson() {
        double lambda = Math.exp(-std);
        double p = 1.0;
        int res = 0;
        do {
            p *= random.nextDouble();
            res++;
        } while (p > lambda);
        return res - 1;
    }

    public List<Double> generateUniform(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(std + random.nextDouble() * (this.mean - this.std));
        }
        return list;
    }
}
