package org.example.io;

import java.util.List;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBWriter implements Writer {
    private static Logger logger = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(DBWriter.class.getName());
    }

    private Connection connection = null;
    private static final String TABLE_NAME = "TEST_TABLE";

    public DBWriter(String path) {
        try {
            String url = "jdbc:sqlite:" + path + "/test.db";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void createOrReplace() {
        query("drop table " + TABLE_NAME + ";");
        query("create table " + TABLE_NAME + "(TEST_COLUMN REAL);");
    }

    private void query(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    @Override
    public void write(List<Double> points) {
        createOrReplace();
        try {
            Statement statement = connection.createStatement();
            for (double p : points) {
                statement.addBatch("insert into " + TABLE_NAME + " values(" + p + ");");
            }
            statement.executeBatch();
            connection.commit();
            statement.close();
            logger.info("Insert into " + TABLE_NAME + " committed");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
