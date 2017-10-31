package com.forecast.demand.model;

/**
 * Created by tuxi1 on 9/2/2017.
 */
public enum ColumnType {
    STRING("string"),
    INTEGER("integer"),
    BOOLEAN("boolean"),
    DATETIME("datetime"),
    DECIMAL("decimal");

    private final String name;

    private ColumnType(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
