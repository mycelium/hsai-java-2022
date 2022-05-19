package spbstu.hsai.consoleGenerator.generator;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GeneratorsTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void invalidBoundsException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid bounds for uniform distribution");

        UniformGenerator.getGenerator(1000, 8);
    }

    @Test
    public void stddevException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Standard deviation must be greater than 0");

        NormalGenerator.getGenerator(1.5, -2);
    }

    @Test
    public void poissonMeanException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Mean must be greater than 0");

        PoissonGenerator.getGenerator(-50);
    }
}
