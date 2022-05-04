package ru.spbstu.telematics.analyzer.normality;

import ru.spbstu.telematics.parameters.ParametersCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class NormalityAnalyzer {

    static public List<Boolean> isNormal(List<? extends List<Double>> sample) {
        return sample.stream()
                .map(NormalityAnalyzer::isNormalOne)
                .collect(Collectors.toList());
    }

    static private boolean isNormalOne(List<Double> sample) {
        List<Double> copy = sample.stream()
                .sorted()
                .collect(Collectors.toList());
        double quartile25 = ParametersCalculator.percentile(copy, 0.25).get();
        double quartile75 = ParametersCalculator.percentile(copy, 0.75).get();
        double d = quartile75 - quartile25;
        double x1 = Math.max(copy.get(0), quartile25 - 3 * d / 2);
        double x2 = Math.min(copy.get(copy.size() - 1), quartile75 + 3 * d / 2);
        long n = copy.stream()
                .filter(value -> ((value < x1) || (value > x2)))
                .count();
        double spikeRate = (double) n / copy.size();
        return (0.0065 < spikeRate && spikeRate < 0.0075);
    }
}
