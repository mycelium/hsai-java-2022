package org.polytech.Output;

import java.util.function.Function;

public enum OutputType {
    CSV, SQLite;

    public Function<String, Output> toOutput() {
        System.out.println("Output type: " + this.name());

        return switch (this) {
            case CSV -> CSVOutput::new;
            case SQLite -> SQLiteOutput::new;
        };
    }
}
