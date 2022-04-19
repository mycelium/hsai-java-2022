package data;

import enums.DistributionType;
import enums.WriterType;

import java.util.Optional;

public class AppParams {
    private DistributionType distributionType;

    // distribution params
    private Optional<Double> origin = Optional.empty();
    private Optional<Double> bound = Optional.empty();
    private Optional<Double> mean = Optional.empty();
    private Optional<Double> stddev = Optional.empty();

    private int number;
    private WriterType writerType;

    private String targetDirectory;

    public AppParams setDistributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
        return this;
    }

    public AppParams setOrigin(double origin) {
        this.origin = Optional.of(origin);
        return this;
    }

    public AppParams setBound(double bound) {
        this.bound = Optional.of(bound);
        return this;
    }

    public AppParams setMean(double mean) {
        this.mean = Optional.of(mean);
        return this;
    }

    public AppParams setStddev(double stddev) {
        this.stddev = Optional.of(stddev);
        return this;
    }

    public AppParams setNumber(int number) {
        this.number = number;
        return this;
    }

    public AppParams setWriterType(WriterType writerType) {
        this.writerType = writerType;
        return this;
    }

    public AppParams setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
        return this;
    }

    public DistributionType getDistributionType() {
        return distributionType;
    }

    public Optional<Double> getOrigin() {
        return origin;
    }

    public Optional<Double> getBound() {
        return bound;
    }

    public Optional<Double> getMean() {
        return mean;
    }

    public Optional<Double> getStddev() {
        return stddev;
    }

    public int getNumber() {
        return number;
    }

    public WriterType getWriterType() {
        return writerType;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }
}
