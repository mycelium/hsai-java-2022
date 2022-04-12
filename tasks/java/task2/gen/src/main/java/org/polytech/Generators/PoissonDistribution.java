package org.polytech.Generators;

import java.util.List;
import java.util.Random;

// Class to generate numbers using poisson distribution

public record PoissonDistribution(double lambda) implements Generator {

    private static Random fRandom = new Random();

    private double generateNext() {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;
        do {
            p *= fRandom.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    @Override
    public List<Double> generate(int n) {
        var list = new java.util.ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            list.add(generateNext());
        }
        return list;
    }

}
