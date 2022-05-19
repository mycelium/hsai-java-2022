package spbstu.hsai.consoleGenerator.generator;

import java.util.Random;

public abstract class Generator {

    protected final Random random = new Random();

    public abstract double getValue();
}
