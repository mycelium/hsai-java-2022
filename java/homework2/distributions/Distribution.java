package homework2.distributions;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public interface Distribution {
    Random random = new Random();
    ArrayList<Double> data = new ArrayList<Double>();
    Logger log = Logger.getLogger(Distribution.class.getName());

    ArrayList<Double> generate(int number);

}
