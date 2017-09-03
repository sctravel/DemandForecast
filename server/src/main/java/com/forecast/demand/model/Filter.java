package com.forecast.demand.model;

public class Filter {
    String columnName;
    String value;
    FilterOperation operation;

    public String getColumnName() {
        return this.columnName;
    }

    public String getValue() {
        return this.value;
    }

    public FilterOperation getOperation() {
        return this.operation;
    }
}
