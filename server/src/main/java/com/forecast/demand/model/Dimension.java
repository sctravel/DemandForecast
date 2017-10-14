package com.forecast.demand.model;

import java.util.List;

/**
 * Created by tuxi1 on 8/30/2017.
 */
public class Dimension {

    private String name;
    private String displayName;
    private List<String> levels;

    public Dimension(String name, String displayName, List<String> levels) {
        this.name = name;
        this.displayName = displayName;
        this.levels = levels;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLevels() {
        return levels;
    }
}
