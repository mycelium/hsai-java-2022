package homework2.IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB implements Output {
    private Connection connection;
    private Statement statement;


    public DB(File file){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            log.severe(ex.getMessage());
        }
        try {
            log.severe("Connecting to database...");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getName());
            connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            log.info("Successfully connected");
        } catch (SQLException ex) {
            log.severe("Connection not established: " + ex.getMessage());
        }
        createTable();
    }

    public void write(ArrayList<Double> data) throws IOException {
        data.forEach((d) -> {
            try {
                log.severe("Inserting values...");
                statement.execute("INSERT INTO DATA VALUES (" + d + ");");
            } catch (SQLException e) {
                log.severe("Couldn't insert values: " + e.getMessage());
            }
        });
        try{
            log.severe("Writing data to database...");
            connection.commit();
        } catch (java.sql.SQLException e){
            log.severe("Error while commiting: " + e.getMessage());

        }
        log.severe("Successfully written data");
        close();
    }

    private void createTable() {
        try {
            log.severe("Creating table...");
            statement.execute("DROP TABLE IF EXISTS DATA;");
            statement.execute("CREATE TABLE DATA (val DOUBLE);");
            log.severe("Table created");
        } catch (SQLException ex) {
            log.severe("Couldn't create the table: " + ex.getMessage());
        }
    }

    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            log.severe("Couldn't close the connection: " + ex.getMessage());
        }
    }
}
