package com.forecast.demand.model;

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

    public static TimeGrain Lookup(String timeGrain) {
        for (TimeGrain tg : TimeGrain.values()) {
            if (tg.name().equalsIgnoreCase(timeGrain)) {
                return tg;
            }
        }
        return null;
    }

}
