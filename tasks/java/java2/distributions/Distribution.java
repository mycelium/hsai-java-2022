package distributions;
import java.util.Random;

public class Distribution {
    int numberOfValues;
    protected static final Random rnd = new Random();

    public Distribution(int numberOfValues) throws Exception {
        if (numberOfValues < 10000)
            throw new Exception("Input parameter exception! Number of values should be 10000 or more.");
        this.numberOfValues = numberOfValues;
    }
}