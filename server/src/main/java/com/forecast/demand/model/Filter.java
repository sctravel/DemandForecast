package com.forecast.demand.model;

public class Filter {
    String columnName;
    String values;
    FilterOperation operation;

    public String getColumnName() {
        return this.columnName;
    }

    public String getValues() {
        return this.values;
    }

    public FilterOperation getOperation() {
        return this.operation;
    }
}
