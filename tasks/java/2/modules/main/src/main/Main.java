package main;

import java.util.logging.Logger;

import distributions.Normal;
import distributions.Poisson;
import distributions.Uniform;
import input.InputData;
import output.WriterCSV;
import output.WriterDataBase;

//параметры приложения
//Main [распределение] [параметр1] [параметр2] [число генерируемых значений] [формат выхода] [директория для записи результата]
//распределение: 
//				U - равномерное на отрезке [параметр1, параметр2]
//				N - нормальное, параметр1 - математическое ожидание, параметр2 - среднеквадратичное отклонение
//				P - Пуассона, только параметр1
//число генерируемых значений: от 10 000
//формат выхода: csv или database
public class Main {
	public static void main(String[] args) {
		try {
			Logger logger = Logger.getLogger("Main");
			logger.info("Application work started");
			InputData input = new InputData(args);
			
			String directory = input.getDirectory();
			
			double[] data = new double[input.getNumber()];
			switch(input.getDistribution()) {
				case "U": new Uniform(input.getParameter1(), input.getParameter2()).getData(data); break;
				case "N": new Normal(input.getParameter1(), input.getParameter2()).getData(data); break;
				case "P": new Poisson(input.getParameter1()).getData(data); break;
			}
			
			StringBuilder output = new StringBuilder();
			output.append(directory);
			output.append("\\result");
			switch(input.getFormat()) {
				case "csv": output.append(".csv"); new WriterCSV(output.toString()).write(data); break;
				case "database": output.append(".db"); new WriterDataBase(output.toString()).write(data); break;
			}
			
			System.out.println("\nOutput file is here: " + output + "\n");
			logger.info("Application work completed");
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
