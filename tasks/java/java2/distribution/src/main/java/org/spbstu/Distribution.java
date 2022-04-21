package org.spbstu;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class Distribution {

    protected static final Random random = new Random();

    abstract double next();

    List<Double> until(int n) { return IntStream.range(0, n).mapToObj(x -> next()).toList(); }

}
