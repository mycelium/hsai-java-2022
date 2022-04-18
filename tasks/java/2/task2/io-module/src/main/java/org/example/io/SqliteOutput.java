package org.example.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.example.log.Log;

public class SqliteOutput implements Output {

    private final Log log = new Log(this.getClass());

    private final Connection connection;

    private static final String JDBC_SQLITE = "jdbc:sqlite:";

    private static final String TABLE_NAME = "DATA";
    private static final String COLUMN_NAME = "VALUE";

    public SqliteOutput(String path) {
        connection = connect(path);
        createOrReplaceTable();
    }

    private Connection connect(String path) {
        try {
            String url = JDBC_SQLITE + path;
            Connection connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            log.info("Connected to database");
            return connection;
        } catch (SQLException e) {
            log.error("Error connect to database!", e.getMessage());
            System.exit(0);
        }
        return null;
    }

    private void createOrReplaceTable() {
        executeUpdate("DROP TABLE " + TABLE_NAME + ";");
        executeUpdate("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME + " DOUBLE);");
    }

    @Override
    public void save(List<Double> data) {
        try {
            log.info("Start saving data!");
            Statement stmt = connection.createStatement();
            data.forEach(x -> {
                try {
                    stmt.addBatch("INSERT INTO " + TABLE_NAME + " VALUES(" + x + ");");
                } catch (SQLException e) {
                    log.error("Error insert in table!");
                }
            });
            stmt.executeBatch();
            connection.commit();
            stmt.close();
            log.info("End saving data!");
        } catch (SQLException e) {
            log.error("Error saving data!");
        }
    }

    private void executeUpdate(String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            log.error("Error executing update: ", sql);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            log.info("Connection to database closed");
        } catch (SQLException e) {
            log.error("Error close connection!");
        }
    }
}
