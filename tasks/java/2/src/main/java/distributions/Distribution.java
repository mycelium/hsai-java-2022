package distributions;

import java.util.Random;

public abstract class Distribution {

    protected static final Random rnd = new Random();

    public abstract Double nextValue();
}
