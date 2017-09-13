package com.forecast.demand.model;

import java.util.Map;
/**
 * Created by tuxi1 on 8/30/2017.
 */
public class MeasureAdjustment {
    private Map<String, String> dimensionValues;
    private String measureName;
    private double newValue, oldValue;
    //private Policy adjustPolicy

    public MeasureAdjustment(Map<String, String> dimValues, String measureName, double oldValue, double newValue) {
        this.dimensionValues = dimValues;
        this.measureName = measureName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Map<String, String> getDimensionValues() {
        return dimensionValues;
    }

    public String getMeasureName() {
        return measureName;
    }

    public double getOldValue() {
        return oldValue;
    }

    public double getNewValue() {
        return newValue;
    }
}
