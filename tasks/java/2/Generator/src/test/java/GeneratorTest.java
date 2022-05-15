import org.testng.Assert;
import org.testng.annotations.Test;
import ru.spbstu.distr.core.AbstractGenerator;
import ru.spbstu.distr.core.NormalGenerator;
import ru.spbstu.distr.core.PoissonGenerator;
import ru.spbstu.distr.core.UniformGenerator;

public class GeneratorTest {

    private AbstractGenerator generator;

    @Test
    public void testNormalWithInvalidArgs() {
        generator = new NormalGenerator(10, -34);
        Assert.assertEquals(generator.getParams()[1], 1, "Expected default value s = 1.0");
    }

    @Test
    public void testPoissonWithInvalidArgs() {
        generator = new PoissonGenerator(-9);
        Assert.assertEquals(generator.getParams()[0], 1, "Expected default value lambda = 1.0");
    }

    @Test
    public void testUniformWithInvalidArgs() {
        generator = new UniformGenerator(9, -2);
        Assert.assertEquals(generator.getParams()[0], -2, "Expected min(left,right)");
        Assert.assertEquals(generator.getParams()[1], 9, "Expected max(left,right)");
    }
}
