package spbstu.hsai.consoleGenerator.io;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import spbstu.hsai.consoleGenerator.io.input.AppParams;
import spbstu.hsai.consoleGenerator.io.input.DistributionType;
import spbstu.hsai.consoleGenerator.io.input.InputReader;
import spbstu.hsai.consoleGenerator.io.input.OutputFormat;

public class InputReaderTest {

    @Test
    public void correctOptions() {
        String[] args = {"-t", "uniform", "-a", "5", "-b", "10", "-n", "10200", "-f", "csv", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        AppParams actualParams = new InputReader().getParams(args);

        double delta = 1e-7;
        assertEquals(DistributionType.UNIFORM, actualParams.getType());
        assertEquals(5.0, actualParams.getMinimum().get(), delta);
        assertEquals(10.0, actualParams.getMaximum().get(), delta);
        assertEquals(10200, actualParams.getNumber());
        assertEquals(OutputFormat.CSV, actualParams.getFormat());
        assertEquals("C:\\Users\\Саша\\Desktop\\hsai-java-2022", actualParams.getOutputDir());
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void missingTypeException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Missing required option: t");

        String[] args = {"-a", "5", "-b", "10", "-n", "10200", "-f", "csv", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        new InputReader().getParams(args);
    }

    @Test
    public void missingFormatException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Missing required option: f");

        String[] args = {"-t", "normal", "-a", "5", "-b", "10", "-n", "10200", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        new InputReader().getParams(args);
    }

    @Test
    public void missingNumberException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Missing required option: n");

        String[] args = {"-t", "normal", "-a", "5", "-b", "10", "-f", "csv", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        new InputReader().getParams(args);
    }

    @Test
    public void missingDirectoryException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Missing required option: o");

        String[] args = {"-t", "normal", "-a", "5", "-b", "10", "-n", "10200", "-f", "csv"};
        new InputReader().getParams(args);
    }

    @Test
    public void invalidDistributionException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid distribution: exponential");

        String[] args = {"-t", "exponential", "-a", "5", "-b", "10", "-n", "10200", "-f", "csv", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        new InputReader().getParams(args);
    }

    @Test
    public void invalidFormatException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid format: file");

        String[] args = {"-t", "normal", "-a", "5", "-b", "10", "-n", "10200", "-f", "file", "-o", "C:\\Users\\Саша\\Desktop\\hsai-java-2022"};
        new InputReader().getParams(args);
    }
}
