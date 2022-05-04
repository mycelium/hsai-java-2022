package ru.spbstu.telematics.parameters;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

public class ParametersCalculatorTest {

    double d = 0.001;
    List<Double> CORRECT = List.of(15., 13., -2.234, 34., -33., 0., 10., -10.);
    List<Double> EMPTY = List.of();
    /**
     * max, min, mean, median
     */
    List<Optional<Double>> listProperties1 =
            List.of(Optional.of(34.0), Optional.of(-33.0), Optional.of(3.34575), Optional.of(5.0));
    List<Optional<Double>> listProperties2 =
            List.of(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    /**
     * 0.25, 0.50, 0.75
     */
    List<Optional<Double>> listPercentile1 =
            List.of(Optional.of(-10.0), Optional.of(0.), Optional.of(13.));
    List<Optional<Double>> listPercentile2 =
            List.of(Optional.empty(), Optional.empty(), Optional.empty());

    @DataProvider(name = "values")
    public Object[] values() {
        return new Object[][] {
                new Object[] {CORRECT, listProperties1},
                new Object[] {EMPTY, listProperties2}
        };
    }

    @DataProvider(name = "percentile")
    public Object[] percentile() {
        return new Object[][] {
                new Object[] {CORRECT, listPercentile1},
                new Object[] {EMPTY, listPercentile2}
        };
    }

    @Test(dataProvider = "values")
    public void correctCalculatorTest(List<Double> samples, List<Optional<Double>> res) {
        Assert.assertEquals(ParametersCalculator.max(samples), res.get(0));
        Assert.assertEquals(ParametersCalculator.min(samples), res.get(1));
        Assert.assertEquals(ParametersCalculator.mean(samples), res.get(2));
        Assert.assertEquals(ParametersCalculator.median(samples), res.get(3));
    }

    @Test(dataProvider = "percentile")
    public void percentileCalculatorTest(List<Double> samples, List<Optional<Double>> res) {
        Assert.assertEquals(ParametersCalculator.percentile(samples, 0.25), res.get(0));
        Assert.assertEquals(ParametersCalculator.percentile(samples, 0.50), res.get(1));
        Assert.assertEquals(ParametersCalculator.percentile(samples, 0.75), res.get(2));
    }

}
