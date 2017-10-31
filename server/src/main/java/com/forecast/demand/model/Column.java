package com.forecast.demand.model;

/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Column {
    //TODO maybe make Measure a subclass of Column
    private String name;
    private ColumnType type;
    private String maxLen;
    private boolean isNullable;
    private String displayName;
    private boolean isMeasure;
    private boolean isEditable;
    private AggregationType aggregationType;
    private String clientExpression;

    public Column(String name, String type, boolean isNullable, String displayName, boolean isMeasure, boolean isEditable, String aggregationType, String clientExpression) {
        this.name = name;
        this.type = ColumnType.valueOf(type.toUpperCase());
        this.isNullable = isNullable;
        this.displayName = displayName;
        this.isEditable = isEditable;
        this.isMeasure = isMeasure;
        this.aggregationType = AggregationType.valueOf(aggregationType);
        this.clientExpression = clientExpression;
    }
    public Column(String name, ColumnType type, boolean isNullable, String displayName, boolean isMeasure, boolean isEditable, AggregationType aggregationType, String clientExpression) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.displayName = displayName;
        this.isEditable = isEditable;
        this.isMeasure = isMeasure;
        this.aggregationType = aggregationType;
        this.clientExpression = clientExpression;
    }

    public String getName() {
        return this.name;
    }

    public ColumnType getType() {
        return this.type;
    }

    public String getmaxLen() {
        return maxLen;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean getIsMeasure() {
        return this.isMeasure;
    }

    public boolean getIsEditable() {
        return this.isEditable;
    }

    public AggregationType getAggregationType() {
        return this.aggregationType;
    }

    public String getClientExpression() {
        return clientExpression;
    }
}
