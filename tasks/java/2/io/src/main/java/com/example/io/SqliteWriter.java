package com.example.io;

import java.sql.*;
import java.util.List;

public class SqliteWriter implements OutputWriter {
    Connection sqliteConnection = null;

    public SqliteWriter(String directoryPath) {
        try {
            String url = "jdbc:sqlite:" + directoryPath + "/test.db";
            sqliteConnection = DriverManager.getConnection(url);
            sqliteConnection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void write(List<Double> series) {

        query("drop table data;");
        query("create table data(x double);");

        try {
            Statement pst = sqliteConnection.createStatement();
            for (double x : series) {
                pst.addBatch("insert into data values(" + x + ");");
            }
            pst.executeBatch();
            sqliteConnection.commit();
            pst.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        try {
            sqliteConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    public void query(String query) {
        try {
            Statement pst = sqliteConnection.createStatement();
            pst.executeUpdate(query);
            pst.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
