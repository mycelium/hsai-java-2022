import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
/*A uniform distribution is described with two parameters - a & b - lower and upper bounds*/
/*Generating Random Integers - in java.util.random to generate a random real number uniformly distributed between 0 and 1,
u can use the "nextDouble" message*/

public class UniformDistribution {
    private static Logger log = Logger.getLogger(UniformDistribution.class.getName());
    private final double a;
    private final double b;
    int size;

    public UniformDistribution(double value, double dev, int s) {
        this.a = value;
        this.b = dev;
        this.size = s;
    }

    public void printList(ArrayList<Double> l) {
        System.out.println("List contents of: ");
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i) + " ");
        }
    }

    private double getNextUniform() {
        double value = new Random().nextDouble(a, b);
        return round(value);
    }

    public ArrayList<Double> getUniformDistribution() {
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < size; i++)
            values.add(getNextUniform());
        log.info("Data has been generated with uniform distribution");
        return values;
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}
