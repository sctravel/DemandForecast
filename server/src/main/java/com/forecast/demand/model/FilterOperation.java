package com.forecast.demand.model;

public enum FilterOperation {
    EQUAL("equal"),
    GREATER("greater"),
    GREATEREQUAL("greaterequal"),
    LESS("less"),
    LESSEQUAL("lessequal"),
    NOTEQUAL("notequal"),
    IN("in"),
    NOTIN("notin");

    private final String name;

    private FilterOperation(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
