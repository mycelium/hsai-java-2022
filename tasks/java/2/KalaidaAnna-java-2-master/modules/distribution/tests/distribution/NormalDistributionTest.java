package distribution;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class NormalDistributionTest {

    @Test
    void testCreateException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NormalDistribution.create(new Random(), 0, -1);
        });

        String expectedMessage = NormalDistribution.MESSAGE_STDDEV_NON_NEGATIVE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetBounds() {
        double mean = 0;
        double stddev = 1;
        NormalDistribution distribution = NormalDistribution.create(new Random(), mean, stddev);

        // 68% of the data should fall within 1 standard deviation of the mean.
        testGetBoundsImpl(distribution, mean - stddev, mean + stddev, 0.68, 0.1);
        // 95% of the data should fall within 2 standard deviations of the mean.
        testGetBoundsImpl(distribution, mean - 2 * stddev, mean + 2 * stddev, 0.95, 0.05);
        // 99.7% of the data should fall within 3 standard deviations of the mean.
        testGetBoundsImpl(distribution, mean - 3 * stddev, mean + 3 * stddev, 0.997, 0.01);
    }

    private void testGetBoundsImpl(NormalDistribution distribution, double from, double to, double expectedPart, double delta) {
        int inIntervalCount = 0;
        int allCount = 1000000;
        for (int i = 0; i < allCount; i++) {
            double value = distribution.get();
            if (from <= value && value <= to) {
                inIntervalCount++;
            }
        }
        assertEquals(expectedPart, (double) inIntervalCount / allCount, delta);
    }
}
