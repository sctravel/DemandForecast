package com.forecast.demand.model;

public enum AggregationType {
    SUM("sum"),
    AVG("avg"),
    COUNT("count"),
    MIN("min"),
    MAX("max"),
    COUNTDISTINCT("countdistinct");

    private final String name;

    private AggregationType(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
