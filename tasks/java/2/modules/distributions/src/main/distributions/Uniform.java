package distributions;

import java.util.Random;
import java.util.logging.Logger;

public class Uniform {

	//случайные данные на отрезке [a,b]
	private double a;
	private double b;
	
	public Uniform(double a, double b) {
		if(a >= b) {
			throw new IllegalArgumentException("The first parameter must be less than the second one");
		}
		this.a = a;
		this.b = b;
	}
	
	public void getData(double[] destination) {
		Random r = new Random();
		Logger logger = Logger.getLogger("Main");
		int valuesGenerated = 1000;
		int size = destination.length;
		logger.info("Data generation started");
		for(int i = 0; i < size; i++) {
			destination[i] = a + r.nextDouble()*(b - a);
			if((i + 1) % valuesGenerated == 0) {
				logger.info(String.format("Values generated:%d out of %d", i + 1, size));
				valuesGenerated += valuesGenerated;
			}
		}
		logger.info("Data generation completed");
	}
}