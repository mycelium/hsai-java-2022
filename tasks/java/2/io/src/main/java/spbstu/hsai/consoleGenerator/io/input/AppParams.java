package spbstu.hsai.consoleGenerator.io.input;

import java.util.Optional;

public class AppParams {

    private DistributionType type;
    private Optional<Double> minimum = Optional.empty();
    private Optional<Double> maximum = Optional.empty();
    private Optional<Double> stddev = Optional.empty();
    private Optional<Double> mean = Optional.empty();
    private int number;
    private OutputFormat format;
    private String outputDir;

    public void setType(DistributionType type) {
        this.type = type;
    }

    public void setMinimum(Double minimum) {
        this.minimum = Optional.of(minimum);
    }

    public void setMaximum(Double maximum) {
        this.maximum = Optional.of(maximum);
    }

    public void setMean(Double mean) {
        this.mean = Optional.of(mean);
    }

    public void setStddev(Double stddev) {
        this.stddev = Optional.of(stddev);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFormat(OutputFormat format) {
        this.format = format;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public DistributionType getType() {
        return type;
    }

    public Optional<Double> getMinimum() {
        return minimum;
    }

    public Optional<Double> getMaximum() {
        return maximum;
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

    public OutputFormat getFormat() {
        return format;
    }

    public String getOutputDir() {
        return outputDir;
    }
}
