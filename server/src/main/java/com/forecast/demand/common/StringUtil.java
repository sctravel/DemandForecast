package com.forecast.demand.common;

/**
 * Created by tuxi1 on 10/13/2017.
 */
public class StringUtil {

    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                   String search) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        return null;
    }
}
