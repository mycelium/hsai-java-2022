package input;

import org.junit.jupiter.api.*;

import distributions.Normal;

public class InputTest {
	
	@Test
	public void failParameters() {
		String[] args = {""};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid number of parameters specified", e.getMessage());
	}
	
	@Test
	public void failDistribution() {
		String[] args = { "F", "0", "3", "10000", "csv", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid distribution specified", e.getMessage());
	}
	
	@Test
	public void failDistributionParameters1() {
		String[] args = { "P", "0", "3", "10000", "csv", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid number of parameters specified", e.getMessage());
	}
	
	@Test
	public void failDistributionParameters2() {
		String[] args = { "N", "a", "b", "10000", "csv", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid distribution parameter specified", e.getMessage());
	}
	
	@Test
	public void failNumberOfValues1() {
		String[] args = { "N", "0", "1", "abc", "csv", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid number of values specified", e.getMessage());
	}
	
	@Test
	public void failNumberOfValues2() {
		String[] args = { "N", "0", "1", "1", "csv", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Number of values is too small (minimum 10 000)", e.getMessage());
	}
	
	@Test
	public void failFormat() {
		String[] args = { "N", "0", "1", "10000", "txt", "output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Invalid output format specified", e.getMessage());
	}
	
	@Test
	public void failDirectory() {
		String[] args = { "N", "0", "1", "10000", "csv", "Q:\\output"};
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new InputData(args));
		Assertions.assertEquals("Failed to create directory", e.getMessage());
	}
}
