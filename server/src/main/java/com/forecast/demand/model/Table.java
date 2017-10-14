package com.forecast.demand.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Table {

    private String owners;
    private String name;
    private String description;
    private List<Column> columns;
    private List<Dimension> dimensions;
    private List<String> measureColumnNames;

    public Table(String name, String owners, List<Column> columns, List<Dimension> dimensions,
                 List<String> measureColumnNames, String description) {
        this.name = name;
        this.owners = owners;
        this.columns = columns;
        this.dimensions = dimensions;
        this.measureColumnNames = measureColumnNames;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getOwners() { return this.owners; }

    public List<Column> getColumns() {
        return this.columns;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Dimension> getDimensions() { return this.dimensions; }

    public List<String> getMeasureColumnNames() { return this.measureColumnNames; }

    public Map<String, Column> getColumnMap() {
        Map<String, Column> map = new HashMap<>();
        for(Column c : columns) {
            map.put(c.getName(), c);
        }
        return map;
    }

}
