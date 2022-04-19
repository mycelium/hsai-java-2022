package distribution;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PoissonDistributionTest {

    @Test
    void testCreateException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PoissonDistribution.create(new Random(), -1);
        });

        String expectedMessage = PoissonDistribution.MESSAGE_MEAN_NON_NEGATIVE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
