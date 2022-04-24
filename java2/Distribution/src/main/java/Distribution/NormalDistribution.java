package Distribution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.logging.Logger;

public class NormalDistribution implements Distribution {

    private final org.apache.commons.math3.distribution.NormalDistribution normalDistribution;
    private static final Logger log;

    static {
        log = Logger.getLogger(NormalDistribution.class.getName());
    }
    public NormalDistribution(double mean, double sd) {
        this.normalDistribution = new org.apache.commons.math3.distribution.NormalDistribution(mean, sd);
    }

    @Override
    public List<Double> generateData(int quantity) {
        log.info("Data generating");
        return IntStream.range(0, quantity)
                .mapToDouble(n -> normalDistribution.sample())
                .boxed().collect(Collectors.toList());
    }
}
