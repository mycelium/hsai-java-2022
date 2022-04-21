package com.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Distribution {
    double generateNext();

    default List<Double> generateAll(int n) {
        return IntStream.range(0, n)
            .mapToDouble(x -> generateNext())
            .boxed()
            .collect(Collectors.toList());
    }
}
