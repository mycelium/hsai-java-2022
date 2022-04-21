package io.output;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;

public class SQLiteWriter implements OutputWriter {
    private int bufferSize = 0;
    private PreparedStatement preparedStatement;
    private final Connection connection;

    private SQLiteWriter(Path file) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS result (value double)");
        statement.execute("PRAGMA synchronous = OFF");
        preparedStatement = connection.prepareStatement("INSERT INTO result (value) VALUES (?)");
        this.connection = connection;
    }

    public static SQLiteWriter of(Path file) throws SQLException {
        return new SQLiteWriter(file);
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void accept(Double value) {
        try {
            preparedStatement.setDouble(1, value);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
