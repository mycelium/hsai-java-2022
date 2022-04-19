package output;

import java.sql.*;

public class DatabaseWriter implements OutputWriter {

    private static final int MAX_BUFFER_SIZE_DEFAULT = 100;

    private final Connection connection;
    private final int maxBufferSize;

    private int curBufferSize = 0;
    private PreparedStatement curStatement = null;

    public DatabaseWriter(String dbFile) throws SQLException {
        this(dbFile, MAX_BUFFER_SIZE_DEFAULT);
    }

    public DatabaseWriter(String dbFile, int bufferSize) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS result (value double)");
        this.connection = connection;
        this.maxBufferSize = bufferSize;
    }

    @Override
    public <T extends Number> void write(T value) {
        String sql = "INSERT INTO result (value) VALUES (?)";
        try {
            if (curBufferSize == 0) {
                curStatement = connection.prepareStatement(sql);
            }
            curStatement.setDouble(1, (double) value);
            curStatement.addBatch();
            curBufferSize++;
            if (curBufferSize >= maxBufferSize) {
                flushBuffer();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        flushBuffer();
        connection.close();
    }

    private void flushBuffer() throws SQLException {
        curBufferSize = 0;
        curStatement.executeBatch();
    }
}
