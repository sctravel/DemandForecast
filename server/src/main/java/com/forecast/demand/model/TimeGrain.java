package com.forecast.demand.model;

import com.forecast.demand.common.StringUtil;

/**
 * Created by tuxi1 on 10/30/2017.
 */
public enum TimeGrain {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    QUARTERLY("quarterly"),
    YEARLY("yearly");

    private final String name;

    private TimeGrain(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }


}
