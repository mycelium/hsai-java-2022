package Generator;

import java.util.Random;
import java.util.logging.Logger;

public abstract class  Generator {
    protected static final Logger log = Logger.getLogger(Generator.class.getName());
    protected int numberOfValues;
    protected final Random random = new Random();

    Generator(int n){
        numberOfValues = n;
    }

    public abstract double genValue();

    public abstract String genValues();
}
