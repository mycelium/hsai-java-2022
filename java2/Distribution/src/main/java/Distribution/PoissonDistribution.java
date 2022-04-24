package Distribution;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PoissonDistribution implements Distribution {

    private final org.apache.commons.math3.distribution.PoissonDistribution poissonDistribution;
    private static final Logger log;

    static {
        log = Logger.getLogger(PoissonDistribution.class.getName());
    }

    public PoissonDistribution(double p) {
        this.poissonDistribution = new org.apache.commons.math3.distribution.PoissonDistribution(p);
    }

    @Override
    public List<Double> generateData(int quantity) {
        log.info("Data generating");
        return IntStream.range(0, quantity)
                .mapToDouble(n -> poissonDistribution.sample())
                .boxed().collect(Collectors.toList());
    }
}
