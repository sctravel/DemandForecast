package com.forecast.demand.model;

import java.util.List;
/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Table {
    private String name;
    private String description;
    private List<Column> columns;

    public Table(String name, List<Column> columns, String description) {
        this.name = name;
        this.columns = columns;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public String getDescription() {
        return this.description;
    }
}
