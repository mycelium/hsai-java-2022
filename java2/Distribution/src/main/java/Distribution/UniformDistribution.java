package Distribution;

import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniformDistribution implements Distribution {

    private final UniformRealDistribution uniformRealDistribution;
    private static final Logger log;

    static {
        log = Logger.getLogger(UniformDistribution.class.getName());
    }

    public UniformDistribution(double min, double max) {
        this.uniformRealDistribution = new UniformRealDistribution(min, max);
    }

    @Override
    public List<Double> generateData(int quantity) {
        log.info("Data generating");
        return IntStream.range(0, quantity)
                .mapToDouble(n -> uniformRealDistribution.sample())
                .boxed().collect(Collectors.toList());
    }
}
