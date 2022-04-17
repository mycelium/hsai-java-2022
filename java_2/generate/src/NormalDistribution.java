import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/*A normal distribution is determined by two parameters the mean and the variance
* in java we can use java.util.Random - nextGaussian()*/

public class NormalDistribution {
    private static Logger log = Logger.getLogger(NormalDistribution.class.getName());
    private final double average;// u
    private double stdDeviation; // v   среднеквадрат отклонение
    int size;

    public NormalDistribution(double val, double dev, int s) {
        this.average = val;
        this.stdDeviation = dev;
        this.size = s;
    }

    public void printList(ArrayList<Double> l){
        System.out.println("List contents of: ");
        for (int i = 0; i < l.size(); i++){
            System.out.println(l.get(i) + " ");
        }
    }
    private double getNextGaussian() {
        double value = new Random().nextGaussian() * stdDeviation + average;
        return round(value, 1);
    }

    public ArrayList<Double> getNormalDistribution() {
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < size; i++)
            values.add(getNextGaussian());
        log.info("Data has been generated with normal distribution");
        return values;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}
