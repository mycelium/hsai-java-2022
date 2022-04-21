module io {
    requires java.base;
    requires java.sql;

    uses java.sql.Driver;

    exports com.example.io;
}