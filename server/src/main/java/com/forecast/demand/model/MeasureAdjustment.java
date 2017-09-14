package com.forecast.demand.model;

import java.util.Map;
/**
 * Created by tuxi1 on 8/30/2017.
 */
public class MeasureAdjustment {
    private Map<String, String> dimensionValues;
    private String tableName, measureName;
    private Double newValue, oldValue;
    //private Policy adjustPolicy

    public MeasureAdjustment(String tableName, Map<String, String> dimValues,
                             String measureName, double oldValue, double newValue) {
        this.tableName = tableName;
        this.dimensionValues = dimValues;
        this.measureName = measureName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getDimensionValues() {
        return dimensionValues;
    }

    public String getMeasureName() {
        return measureName;
    }

    public Double getOldValue() {
        return oldValue;
    }

    public Double getNewValue() {
        return newValue;
    }
}
