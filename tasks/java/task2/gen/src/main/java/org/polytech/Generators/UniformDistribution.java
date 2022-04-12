package org.polytech.Generators;

import java.util.List;

public record UniformDistribution(double min, double max) implements Generator {

    @Override
    public List<Double> generate(int n) {
        var list = new java.util.ArrayList<Double>(n);
        for (int i = 0; i < n; i++) {
            list.add(Math.random() * (max - min) + min);
        }
        return list;
    }
}
