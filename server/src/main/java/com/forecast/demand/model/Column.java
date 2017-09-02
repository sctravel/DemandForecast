package com.forecast.demand.model;

/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Column {
    private String name;
    private ColumnType type;

    public Column(String name, String type) {
        this.name = name;
        this.type = ColumnType.valueOf(type);
    }

    public String getName() {
        return this.name;
    }

    public ColumnType getType() {
        return this.type;
    }
}
