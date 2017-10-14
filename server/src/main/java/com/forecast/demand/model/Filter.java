package com.forecast.demand.model;

import com.forecast.demand.common.StringUtil;

import java.util.List;

public class Filter {
    String columnName;
    ColumnType columnType;
    List<String> values;
    FilterOperation operation;

    public Filter(String columnName, String columnType, List<String> values, String operation) {
        this.columnName = columnName;
        this.columnType = StringUtil.searchEnum(ColumnType.class, columnType);
        this.values = values;
        this.operation = StringUtil.searchEnum(FilterOperation.class, operation);
    }

    public String getColumnName() {
        return this.columnName;
    }

    public List<String> getValues() {
        return this.values;
    }

    public FilterOperation getOperation() {
        return this.operation;
    }

    public ColumnType getColumnType() {
        return columnType;
    }
}
