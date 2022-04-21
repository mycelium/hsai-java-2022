package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

public class SqliteWriter implements Writer {

    private static final Logger log = Logger.getLogger(SqliteWriter.class.getName());

    private Connection connection;

    public SqliteWriter(String file) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            connection.setAutoCommit(false);
            log.info("Connected to Sqlite database");
        } catch (SQLException e) {
            log.info("Error connecting to Sqlite database");
        }

        dropTable();
        createTable();
    }

    private void dropTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE GEN_VALUES;");
            statement.close();
            log.info("Table dropped!");
        } catch (SQLException e) {
            log.warning("Error dropping table!");
        }
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE GEN_VALUES (GEN_VALUE DOUBLE);");
            statement.close();
            log.info("Table created!");
        } catch (SQLException e) {
            log.warning("Error creating table!");
        }
    }

    @Override
    public void write(List<Double> data) {
        try {
            Statement statement = connection.createStatement();
            data.forEach(x -> {
                try {
                    statement.addBatch("INSERT INTO GEN_VALUES VALUES(" + x + ");");
                } catch (SQLException e) {
                    log.warning("Error writing value to Sqlite database!");
                }
            });

            statement.executeBatch();
            connection.commit();
            statement.close();
            connection.close();

            log.info("Wrote values to Sqlite database");
        } catch (SQLException e) {
            log.warning("Error writing values to Sqlite database!");
        }
    }
}
