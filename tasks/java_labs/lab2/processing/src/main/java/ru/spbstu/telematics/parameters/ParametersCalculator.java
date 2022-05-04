package ru.spbstu.telematics.parameters;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParametersCalculator {

    public static Optional<Double> mean(List<Double> sample) {
        if (sample == null || sample.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(sample.stream().mapToDouble(d -> d).sum() / sample.size());
    }

    public static Optional<Double> median(List<Double> sample) {
        if (sample == null || sample.size() == 0) {
            return Optional.empty();
        }
        List<Double> sortedSample = sample.stream().sorted().collect(Collectors.toList());
        int indexMedian = sortedSample.size() / 2;
        if (sortedSample.size() % 2 == 0) {
            return Optional.of((sortedSample.get(indexMedian - 1) + sortedSample.get(indexMedian)) / 2);
        } else {
            return Optional.of(sortedSample.get(indexMedian));
        }
    }

    public static Optional<Double> max(List<Double> sample) {
        if (sample == null) {
            return Optional.empty();
        }
        return sample.stream().max(Double::compareTo);
    }

    public static Optional<Double> min(List<Double> sample) {
        if (sample == null) {
            return Optional.empty();
        }
        return sample.stream().min(Double::compareTo);
    }

    public static Optional<Double> percentile(List<Double> latencies, double percentile) {
        var list = latencies.stream().sorted().collect(Collectors.toList());
        int index = (int) Math.ceil(percentile * list.size());
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(index-1));
    }
}

