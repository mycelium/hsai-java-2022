import java.util.ArrayList;
import java.util.logging.Logger;

/*A normal distribution is determined by one parameter the lambda*/

public class PoissonDistribution {
    private static Logger log = Logger.getLogger(PoissonDistribution.class.getName());
    private final Double lambda;
    int size;

    public PoissonDistribution(double val, int s) {
        this.lambda = val;
        this.size = s;
    }

    public void printList(ArrayList<Double> l) {
        System.out.println("List contents of: ");
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i) + " ");
        }
    }

    public static Double getNextPoisson(Double l) {
        double t = 0;
        for (Double x = 0.0; true; x++) {
            t = t - Math.log(Math.random()) / l;
            if (t > 1.0) {
                return x;
            }
        }
    }

    public ArrayList<Double> getPoissonDistribution() {
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < size; i++)
            values.add(getNextPoisson(lambda));
        log.info("Data has been generated with poisson distribution");
        return values;
    }

}
