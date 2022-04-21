package io.parameters;

import distribution.Distribution;

import java.util.stream.Stream;

public class StreamCreator {
    public Stream<Double> createStream(Distribution<Double> distribution, int limit) {
        return Stream.generate(distribution).limit(limit);
    }
}
