package ru.spbstu.distr.core;

import java.util.Random;

public abstract class AbstractGenerator {
    protected final Random random = new Random();

    public abstract double nextValue();

    public abstract double[] getParams();
}
