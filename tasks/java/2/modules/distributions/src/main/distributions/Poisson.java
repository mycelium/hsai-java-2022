package distributions;

import java.util.Random;
import java.util.logging.Logger;

public class Poisson {

	double lambda;
	
	public Poisson(double lambda) {
		if(lambda <= 0) {
			throw new IllegalArgumentException("Distribution parameter must be positive");
		}
		this.lambda = lambda;
	}
	
	public void getData(double[] destination) {
		Random r = new Random();
		Logger logger = Logger.getLogger("Main");
		int valuesGenerated = 1000;
		int size = destination.length;
		logger.info("Data generation started");
		for(int i = 0; i < size; i++) {
			int res = -1;
			double sum = 0;
			do {
				sum += r.nextExponential();
				res++;
			} while(sum < lambda);
			destination[i] = (double) res;
			if((i + 1) % valuesGenerated == 0) {
				logger.info(String.format("Values generated:%d out of %d", i + 1, size));
				valuesGenerated += valuesGenerated;
			}
		}
		logger.info("Data generation completed");
	}
}