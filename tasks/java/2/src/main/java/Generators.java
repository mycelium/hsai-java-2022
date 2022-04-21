import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record Generators(double std, double mean) {

    private static final Random random = new Random();

    public List<String> generateNormal(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(String.valueOf(random.nextGaussian() * mean + std));
        }
        return list;
    }

    public List<String> generatePoisson(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generateNextPoisson().toString());
        }
        return list;
    }

    private Integer generateNextPoisson() {
        double lambda = Math.exp(-std);
        double p = 1.0;
        int res = 0;
        do {
            p *= random.nextDouble();
            res++;
        } while (p > lambda);
        return res - 1;
    }

    public List<String> generateUniform(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(String.valueOf(std + random.nextDouble(mean - std + 1)));
        }
        return list;
    }
}
