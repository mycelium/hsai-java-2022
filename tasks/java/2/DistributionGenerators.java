import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record DistributionGenerators(double std, double mean) {

    static final Random random = new Random();

    public List<Double> NormalDistribution(long n) {
        List<Double> NormalList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            NormalList.add(random.nextGaussian() * mean + std);
        }
        return NormalList;
    }

    public List<Integer> PoissonDistribution(long n) {
        List<Integer> PoissonList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            PoissonList.add(NextPoissonGeneration());
        }
        return PoissonList;
    }

    private int NextPoissonGeneration() {
        double lambda = Math.exp(-std);
        double p = 1.0;
        int result = 0;
        do {
            p *= random.nextDouble();
            result++;
        } while (p > lambda);
        return result - 1;
    }

    public List<Double> UniformDistribution(long n) {
        List<Double> UniformList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            UniformList.add(std + random.nextDouble(mean - std + 1));
        }
        return UniformList;
    }
}