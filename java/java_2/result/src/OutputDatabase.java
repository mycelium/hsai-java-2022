import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class OutputDatabase {

    private static Connection connection = null;
    private static Statement statement = null;
    private static final Logger log = Logger.getLogger(OutputDatabase.class.getName());

    public void outputDatabase(List<Double> data) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/veres/IdeaProjects/java_2/result/src/data.sqlite");
            log.info("Opened database successfully");

            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS DATA;");
            statement.execute("CREATE TABLE DATA (VALUE DOUBLE);");
            statement.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        log.info("Table created successfully");

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/veres/IdeaProjects/java_2/result/src/data.sqlite");
        statement = connection.createStatement();
        data.forEach((value) -> {
            try {
                statement.execute("INSERT INTO DATA VALUES (" + value + ");");
            } catch (SQLException ex) {
                log.info("Output failed" + ex.getMessage());
            }
        });
        log.info("Data saved in database in table DATA");
        statement.close();
        connection.close();
    }
}
