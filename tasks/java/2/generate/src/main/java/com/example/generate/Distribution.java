package com.example.generate;

import java.util.Random;

public abstract class Distribution {
    private final Random r;

    protected Distribution() {
        r = new Random();
    }

    protected Distribution(long seed) {
        r = new Random(seed);
    }

    protected Random getRandom() {
        return r;
    }

    public abstract double getPoint();

    public abstract Distribution getWithArguments(double[] args);
}
