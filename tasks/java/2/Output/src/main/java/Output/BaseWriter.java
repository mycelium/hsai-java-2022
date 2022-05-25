package Output;

import java.sql.*;



public class BaseWriter extends Writer{
    private Connection conn;
    private Statement statmt;
    public BaseWriter(String path){
        super(path);
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + pathToFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Connection complete");

        try {
            statmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statmt.execute("CREATE TABLE base (id INTEGER, value REAL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("Base was created");

    }
    @Override
    public void write(String s) {
        log.info("Filling base started");

        String[] lines = s.split("\\n");
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO base (id, value) VALUES(?,?)")) {
            for (int i = 0; i < lines.length; i++) {
                String[] qwe = lines[i].split(",");
                statement.setLong(1, Integer.valueOf(qwe[0]));
                statement.setDouble(2, Double.valueOf(qwe[1]));
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
            statmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Connection closed");
    }
}
