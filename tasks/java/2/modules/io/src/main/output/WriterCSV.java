package output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class WriterCSV {

	String file;
	
	public WriterCSV(String file) {
		this.file = file;
	}
	
	public void write(double[] data) throws IOException {
		StringBuilder result = new StringBuilder();
		int valuesRecorded = 1000;
		int size = data.length;
		Logger logger = Logger.getLogger("Main");
		logger.info("Data recording started");
		result.append(data[0]);
		for(int i = 1; i < size; i++) {
			result.append(",");
			result.append(data[i]);
			if((i + 1) % valuesRecorded == 0) {
				logger.info(String.format("Values recorded:%d out of %d", i + 1, size));
			}
		}
		Files.writeString(Paths.get(file), result);
		logger.info("Data recording completed");
	}
}
