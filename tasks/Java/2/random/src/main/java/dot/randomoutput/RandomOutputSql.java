package dot.randomoutput;
import java.nio.file.Path;
import dot.randominterfaces.RandomInterfaces.RandomStorablenArgumentsVisible;
import picocli.CommandLine.Option;
import java.sql.*;

public class RandomOutputSql implements RandomStorablenArgumentsVisible<Double[]>{
    @Option(names = "-sql", required = true, description = "Store the results in the Sqlite database.")
    Boolean bRandomOutputSql;
    public RandomOutputSql(){};
    @Override
    public void store(Double[] data, Path path) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:"+path.toString());
            Statement  stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS RANDOMS(random REAL NOT NULL)";
            stmt.executeUpdate(sql);
            for (Double random : data) {
                sql=String.format("INSERT INTO RANDOMS(random) VALUES(%s)", String.valueOf(random));
                stmt.executeUpdate(sql);
            }
            stmt.close();
            c.close();
          } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
          }
    }

    @Override
    public String showArguments() {
        return "Sqlite database output format";
    }
}
