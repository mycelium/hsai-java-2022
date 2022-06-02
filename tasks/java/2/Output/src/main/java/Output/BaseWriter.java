package Output;

import java.sql.*;
import java.util.List;


public class BaseWriter extends Writer{
    private Connection conn;
    private Statement statement;
    public BaseWriter(String path) {
        super(path);
    }

    void openDataBase(){
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + pathToFile + "\\base");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Connection complete");

        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.execute("DROP TABLE IF EXISTS base;");
            statement.execute("CREATE TABLE base (value REAL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("Base was created");

    }
    @Override
    public void write(List<Double> l) {
        openDataBase();

        StringBuilder stringBuilder = new StringBuilder();
        l.forEach((elem) -> stringBuilder.append(elem).append(","));
        log.info("Filling base started");

        String s = stringBuilder.toString();

        String[] lines = s.split(",");
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO base (value) VALUES(?)")) {
            for (int i = 0; i < lines.length; i++) {
                statement.setDouble(1 , Double.parseDouble(lines[i]));
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("Filling base finished");

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Connection closed");
    }
}
