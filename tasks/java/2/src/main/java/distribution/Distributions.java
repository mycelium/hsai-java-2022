package main.java.distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Distributions {
    private double std;
    private double mean;
    private static final Random random = new Random();

    Distributions(double std, double mean) {
        this.std = std;
        this.mean = mean;
    }

    public List<Double> normalDistribution(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(random.nextGaussian() * mean + std);
        }
        return list;
    }

    public List<Double> poissonDistribution(int n) {
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

    public List<Double> uniformDistribution(int n) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(std + random.nextDouble() * (this.mean - this.std));
        }
        return list;
    }
}
