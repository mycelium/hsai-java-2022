package org.polytech.Generators;

import java.util.List;
import java.util.Random;

public record NormalDistribution(double std, double mean) implements Generator {

    private static Random fRandom = new Random();

    @Override
    public List<Double> generate(int n) {
        var list = new java.util.ArrayList<Double>(n);
        for (int i = 0; i < n; i++) {
            list.add(fRandom.nextGaussian() * std + mean);
        }
        return list;
    }
}
