import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record DataGeneration(double std, double mean) {
    static final Random rnd = new Random();

    public List<Double> generateUniform(int n) {
        List<Double> valuesList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            valuesList.add(std + rnd.nextDouble(mean - std + 1));
        }
        return valuesList;
    }

    public List<Double> generateNormal(int n) {
        List<Double> valuesList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            valuesList.add(rnd.nextGaussian() * mean + std);
        }
        return valuesList;
    }

    public List<Integer> generatePoisson(int n) {
        List<Integer> valuesList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            valuesList.add(generatePoisson());
        }
        return valuesList;
    }

    private int generatePoisson() {
        double lmb= Math.exp(-std);
        double p = 1.0;
        int res = 0;
        do {
            p *= rnd.nextDouble();
            res++;
        } while (p > lmb);
        return res - 1;
    }

}
