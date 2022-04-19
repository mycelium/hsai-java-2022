package distribution;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ContinuousUniformDistributionTest {

    @Test
    void testCreateException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ContinuousUniformDistribution.create(new Random(), 2, 1);
        });

        String expectedMessage = ContinuousUniformDistribution.MESSAGE_ILLEGAL_BOUNDS;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetBounds() {
        double origin = 0;
        double bound = 2;
        ContinuousUniformDistribution distribution = ContinuousUniformDistribution.create(new Random(), origin, bound);

        double mid = (origin + bound) / 2;
        int lowerCount = 0;
        int upperCount = 0;
        int allCount = 1000000;

        for (int i = 0; i < allCount; i++) {
            double value = distribution.get();
            if (value < mid) {
                lowerCount++;
            } else {
                upperCount++;
            }
        }

        assertEquals(lowerCount, upperCount, allCount / 20.);
    }
}
