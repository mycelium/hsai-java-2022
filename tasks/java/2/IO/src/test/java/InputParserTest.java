import org.testng.Assert;
import org.testng.annotations.Test;
import ru.spbstu.distr.io.InputParser;

public class InputParserTest {

    InputParser inputParser;

    @Test
    public void testValidArgs() {
        String[] args = "-t poisson -p 5 -n 15000 -f sqlite -o my-data".split(" ");
        inputParser = new InputParser(args);
        Assert.assertTrue(inputParser.isValid(), "Provided arguments are incorrect");
    }

    @Test
    public void testWrongArgs() {
        String[] args = "-t bernoulli -p 5 -n 15000 -f sqlite -o my-data".split(" ");
        inputParser = new InputParser(args);
        Assert.assertFalse(inputParser.isValid(), "Provided arguments are correct");
    }
}
