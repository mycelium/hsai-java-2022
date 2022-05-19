package spbstu.hsai.consoleGenerator.io.output;

import java.sql.*;

public class DatabaseOutput extends Output {

    private Connection connection;

    public DatabaseOutput(String file) throws Exception {

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS result (value double)");
        } catch (SQLException e) {
            throw new Exception("SQL error: " + e.getMessage());
        }
    }

    @Override
    public void write(double value) throws Exception {

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO result (value) VALUES (?)")) {
            statement.setDouble(1, value);
            statement.execute();
        } catch (SQLException e) {
            throw new Exception("Unable to write value - SQL error: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {

        try {
            connection.close();
            logger.info("SQL connection closed");
        } catch (SQLException e) {
            throw new Exception("SQL error: " + e.getMessage());
        }
    }
}
