package com.forecast.demand.model;

/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Column {
    //TODO maybe make Measure a subclass of Column
    private String name;
    private ColumnType type;
    private String displayName;
    private boolean isMeasure;
    private boolean isEditable;
    private AggregationType aggregationType;

    public Column(String name, String type, String displayName, boolean isMeasure, boolean isEditable, String aggregationType) {
        this.name = name;
        this.type = ColumnType.valueOf(type.toUpperCase());
        this.displayName = displayName;
        this.isEditable = isEditable;
        this.isMeasure = isMeasure;
        this.aggregationType = AggregationType.valueOf(aggregationType);
    }
    public Column(String name, ColumnType type, String displayName, boolean isMeasure, boolean isEditable, AggregationType aggregationType) {
        this.name = name;
        this.type = type;
        this.displayName = displayName;
        this.isEditable = isEditable;
        this.isMeasure = isMeasure;
        this.aggregationType = aggregationType;
    }

    public String getName() {
        return this.name;
    }

    public ColumnType getType() {
        return this.type;
    }

    public String getDisplayName() { return this.displayName; }

    public boolean getIsMeasure() { return this.isMeasure; }

    public boolean getIsEditable() { return this.isEditable; }

    public AggregationType getAggregationType() { return this.aggregationType; }
}
