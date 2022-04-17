package distributions;

import java.util.Random;

public class Distribution {
    int size;

    protected static final Random rnd = new Random();

    public Distribution(int size) throws Exception {
        if (size < 10000) throw new Exception("Size should be at least 10000");
        this.size = size;
    }
}
