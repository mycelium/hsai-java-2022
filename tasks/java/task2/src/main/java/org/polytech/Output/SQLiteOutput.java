package org.polytech.Output;

import com.google.common.collect.Iterators;
import org.jooq.DSLContext;
import org.jooq.Row1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.polytech.Utils.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static org.jooq.impl.DSL.*;

// Output to SQLite database using JDBC and jOOQ

public class SQLiteOutput implements Output {
    private final String OUTPUT_FILE = "output.db";

    private final String TABLE_NAME = "GENERATED_VALUES";
    private final String COLUMN_NAME = "GENERATED_VALUE";

    private final String directory;

    private DSLContext dbContext;

    public SQLiteOutput(String dir) {
        this.directory = dir;
        try {
            Logger.log("Connecting to database...");
            var connection = DriverManager.getConnection("jdbc:sqlite:" + directory + java.io.File.separator + OUTPUT_FILE);
            dbContext = DSL.using(connection, SQLDialect.SQLITE);
            Logger.log("Connected to database.");
            createTableOrReplace();
        } catch (SQLException e) {
            Logger.log(e.getMessage());
        }
    }

    // Create table VALUES with column value of type double using jOOQ in database
    private void createTableOrReplace() {
        try (var drop = dbContext
                .dropTableIfExists(TABLE_NAME)) {
            drop.execute();
            Logger.log("Old table truncated.");
        }
        try (var create = dbContext
                .createTable(TABLE_NAME)
                .column(COLUMN_NAME, SQLDataType.DOUBLE)) {
            create.execute();
            Logger.log("New table created.");
        }
    }


    @Override
    public void save(List<Double> list) {
        var batches = Iterators.partition(list.iterator(), 10000);
        batches.forEachRemaining(batch -> {
            Collection<Row1<Object>> rows = batch.stream().map(dbl -> row((Object) dbl)).toList();
            try (var insert = dbContext
                    .insertInto(table(TABLE_NAME), field(COLUMN_NAME))
                    .valuesOfRows(rows)) {
                insert.execute();
                Logger.log("Values inserted in SQLite.");
            }
        });
    }
}
