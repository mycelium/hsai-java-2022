package output;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class WriterDataBase {
	
	String file;
	
	public WriterDataBase(String file) {
		this.file = file;
	}
	
	public void write(double[] data) throws SQLException {
		try (Connection connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", file))) {
			Statement statement = connection.createStatement();
			statement.executeUpdate("drop table if exists result");
			statement.executeUpdate("create table result (id integer, value real)");
			int valuesRecorded = 1000;
			int size = data.length;
			Logger logger = Logger.getLogger("Main");
			logger.info("Data recording started");
			for(int i = 0; i < size; i++) {
				statement.executeUpdate(String.format("insert into result (id, value) values (%d, %s)", i + 1, String.valueOf(data[i])));
				if((i + 1) % valuesRecorded == 0) {
					logger.info(String.format("Values recorded:%d out of %d", i + 1, size));
				}
			}
			logger.info("Data recording completed");
		}
	}
}
