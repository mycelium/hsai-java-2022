package homework2.Tests;

import homework2.distributions.Uniform;
import homework2.distributions.Normal;
import homework2.distributions.Poisson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DistributionTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void uniformTest() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Incorrect bounds!");
        Uniform uni1 = new Uniform(5, 1);
        uni1.generate(1000);
    }

    @Test
    public void normalTest() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Incorrect sigma!");
        Normal norm = new Normal(1.3, -1);
        norm.generate(1000);
    }

    @Test
    public void poissonTest() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Incorrect mean value!");
        Poisson pois = new Poisson(-10);
        pois.generate(1000);
    }
}