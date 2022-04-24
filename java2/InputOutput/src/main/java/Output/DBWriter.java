package Output;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

public class DBWriter implements Writer {

    private static final Logger log;

    static {
        log = Logger.getLogger(DBWriter.class.getName());
    }

    private Connection connection;
    private Statement statement;

    public DBWriter(String filePath) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            log.severe(ex.getMessage());
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            connection.setAutoCommit(false);
            this.statement = connection.createStatement();
            log.info("connection established");
        } catch (SQLException ex) {
            log.severe("Connection not established: " + ex.getMessage());
        }
        createTable();
    }

    private void createTable() {
        try {
            statement.execute("DROP TABLE IF EXISTS DATA;");
            statement.execute("CREATE TABLE DATA (val DOUBLE);");
            log.info("Table created");
        } catch (SQLException ex) {
            log.severe("Query failed" + ex.getMessage());
        }
    }


    @Override
    public void write(List<Double> data) {
        try {
            data.forEach((d) -> {
                try {
                    statement.execute("INSERT INTO DATA VALUES (" + d + ");");
                } catch (SQLException ex) {
                    log.severe("Query failed" + ex.getMessage());
                }
            });
            connection.commit();
            log.info("Data written to database");
            close();
        } catch (SQLException ex) {
            log.severe("Query failed" + ex.getMessage());
        }
    }

    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            log.severe("Connection is not closed" + ex.getMessage());
        }
    }
}
