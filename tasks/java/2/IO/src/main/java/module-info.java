module IO {
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires commons.cli;
    exports ru.spbstu.distr.io;
    requires org.apache.logging.log4j;
    requires Generator;
    requires com.opencsv;
}