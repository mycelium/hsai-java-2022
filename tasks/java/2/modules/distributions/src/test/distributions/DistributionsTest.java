package distributions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

public class DistributionsTest {

	@Test
	public void testNormal() {
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Normal(0, 0));
		Assertions.assertEquals("Standart deviation must be positive", e.getMessage());
	}
	
	@Test
	public void testPoisson() {
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Poisson(0));
		Assertions.assertEquals("Distribution parameter must be positive", e.getMessage());
	}
	
	@Test
	public void testUniform() {
		Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Uniform(10, 2));
		Assertions.assertEquals("The first parameter must be less than the second one", e.getMessage());
	}
}
