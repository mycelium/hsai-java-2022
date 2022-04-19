module io {
    requires commons.cli;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    exports input;
    exports output;
    exports enums;
    exports data;
}