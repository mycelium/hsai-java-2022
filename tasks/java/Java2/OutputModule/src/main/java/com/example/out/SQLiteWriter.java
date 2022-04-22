package com.example.out;

import java.util.List;
import java.sql.*;

import java.util.logging.Logger;

public class SQLiteWriter implements Output {
    private static Logger logger = Logger.getLogger(SQLiteWriter.class.getName());;
    private Connection connection = null;
    private static final String tableName = "new_table";

    public SQLiteWriter(String path) {
        try {
            String url = "jdbc:sqlite:" + path + "/sqlite.db";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            logger.info("Connected successfully.");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void write(List<Double> points) {
        runStatement("drop table " + tableName + ";");
        runStatement("create table " + tableName + "(DISTRIBUTION REAL);");
        try {
            Statement statement = connection.createStatement();
            for (double p : points) {
                statement.addBatch("insert into " + tableName + " values(" + p + ");");
            }
            statement.executeBatch();
            connection.commit();
            statement.close();
            logger.info("Data inserted");

        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }
    private void runStatement(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

}
