package input;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputData {

	private String distribution;
	private double parameter1;
	private double parameter2;
	private int number;
	private String format;
	private String directory;
	
	public InputData(String[] args) {
		if(args.length < 5 || args.length > 6) {
			throw new IllegalArgumentException("Invalid number of parameters specified");
		}
		
		int offset = 0;
		distribution = args[0];
		if(distribution.equals("P")) {
			if(args.length != 5) {
				throw new IllegalArgumentException("Invalid number of parameters specified");
			}
			try {
				parameter1 = Double.parseDouble(args[1]);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Invalid distribution parameter specified");
			}
		} else if(distribution.equals("U") || distribution.equals("N")) {
			if(args.length != 6) {
				throw new IllegalArgumentException("Invalid number of parameters specified");
			}
			try {
				parameter1 = Double.parseDouble(args[1]);
				parameter2 = Double.parseDouble(args[2]);
				offset++;
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Invalid distribution parameter specified");
			}
		} else {
			throw new IllegalArgumentException("Invalid distribution specified");
		}
		
		try {
			number = Integer.parseInt(args[2 + offset]);
			if(number < 10000) {
				throw new IllegalArgumentException("Number of values is too small (minimum 10 000)");
			}
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number of values specified");
		}
		
		format = args[3 + offset];
		if(!(format.equals("csv") || format.equals("database"))) {
			throw new IllegalArgumentException("Invalid output format specified");
		}
		
		directory = args[4 + offset];
		File check = new File(directory);
		if(!check.exists()) {
			if(!check.mkdirs()) {
				throw new IllegalArgumentException("Failed to create directory");
			}
		} else if(check.isFile()) {
			throw new IllegalArgumentException("File specified instead of directory");
		}
	}

	public String getDistribution() {
		return distribution;
	}

	public double getParameter1() {
		return parameter1;
	}

	public double getParameter2() {
		return parameter2;
	}

	public int getNumber() {
		return number;
	}

	public String getFormat() {
		return format;
	}

	public String getDirectory() {
		return directory;
	}
}
