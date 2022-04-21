package com.polytech.generator;

import java.util.List;

public interface Distribution {
    List<Double> generate(int n);
    double generateOne();
}
