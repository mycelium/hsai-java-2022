import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record Generators(double std, double mean) {

    private static final Random random = new Random();

    public List<Double> generateNormal(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(random.nextGaussian() * mean + std);
        }
        return list;
    }

    public List<Integer> generatePoisson(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generateNextPoisson());
        }
        return list;
    }

    private int generateNextPoisson() {
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
            list.add(std + random.nextDouble(mean - std + 1));
        }
        return list;
    }
}
