package com.polytech.io;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.polytech.log.ConsoleLogger;
import com.polytech.log.Logger;

public class SQLiteWriter implements Writer{

    private static final Logger logger = new ConsoleLogger(SQLiteWriter.class.getName());

    private final Connection connection;

    private static final String TABLE_NAME = "GENERATED_DATA";
    private static final String COLUMN_NAME = "VALUE";

    public SQLiteWriter(String file) {
        connection = getConnection(file);
        if (!isTableExist()) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME + " DOUBLE);");
                statement.close();
                logger.trace("created table " + TABLE_NAME);
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    private Connection getConnection(String file) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            connection.setAutoCommit(false);
            logger.trace("connect to database");
        } catch (SQLException e) {
            logger.error(e);
        }

        return connection;
    }

    private boolean isTableExist() {
        DatabaseMetaData meta = null;
        try {
            meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, TABLE_NAME, new String[] {"TABLE"});
            return resultSet.next();
        } catch (SQLException e) {
            logger.error(e);
        }

        return false;
    }


    @Override
    public void write(List<Double> data) {
        try {
            Statement statement = connection.createStatement();
            data.forEach(x -> {
                try {
                    statement.addBatch("INSERT INTO " + TABLE_NAME + " VALUES(" + x + ");");
                } catch (SQLException e) {
                    logger.error(e);
                }
            });
            statement.executeBatch();
            connection.commit();
            statement.close();
            connection.close();
            logger.trace("wrote to database");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
