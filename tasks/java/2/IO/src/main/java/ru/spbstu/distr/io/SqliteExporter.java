package ru.spbstu.distr.io;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteExporter extends AbstractExporter {

    private Connection connection;

    public SqliteExporter(Path outputFile) {
        super(outputFile);
    }

    @Override
    public void create() {

        super.create();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + outputFile);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException e) {
            log.error("SQL error occurred");
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE sample (id INTEGER, value REAL);")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
            log.info("SQLite connection closed");
        } catch (SQLException e) {
            log.error("SQL error occurred");
        } catch (NullPointerException npe) {
            log.error("Connection doesn't exist");
        }
    }

    @Override
    public void write(DataEntry entry) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO sample (id, value) VALUES(?,?)")) {
            statement.setLong(1, entry.id());
            statement.setDouble(2, entry.value());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
