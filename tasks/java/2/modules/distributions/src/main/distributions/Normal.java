package distributions;

import java.util.Random;
import java.util.logging.Logger;

public class Normal {
	
	private double mu; //математическое ожидание
	private double sigma; //среднеквадратичное отклонение
	
	public Normal(double mu, double sigma) {
		if(sigma <= 0) {
			throw new IllegalArgumentException("Standart deviation must be positive");
		}
		this.mu = mu;
		this.sigma = sigma;
	}

	public void getData(double[] destination) {
		Random r = new Random();
		Logger logger = Logger.getLogger("Main");
		int valuesGenerated = 1000;
		int size = destination.length;
		logger.info("Data generation started");
		for(int i = 0; i < size; i++) {
			destination[i] = r.nextGaussian(mu, sigma);
			if((i + 1) % valuesGenerated == 0) {
				logger.info(String.format("Values generated:%d out of %d", i + 1, size));
			}
		}
		logger.info("Data generation completed");
	}
}