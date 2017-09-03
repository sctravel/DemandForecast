package com.forecast.demand.common;

import java.time.LocalDate;

public class DateTimeUtil {
    /* This is a custom function for parsing date values like "1-Jul-2017"*/
    public static LocalDate parseDateIndMMMyy(String dateString, String delimiter) {
        if(dateString==null||dateString.trim().isEmpty()) return null;
        String[] arr = dateString.split(delimiter);
        if(arr.length!=3) return null;
        int day = Integer.parseInt(arr[0]);
        int month = getMonth(arr[1]);
        int year = arr[2].length()==2 ? 2000+Integer.parseInt(arr[2]) : Integer.parseInt(arr[2]);
        return LocalDate.of(year, month, day);
    }

    private static int getMonth(String val) {
        if(val.equalsIgnoreCase("Jan")) return 1;
        else if(val.equalsIgnoreCase("Feb")) return 2;
        else if(val.equalsIgnoreCase("Mar")) return 3;
        else if(val.equalsIgnoreCase("Apr")) return 4;
        else if(val.equalsIgnoreCase("May")) return 5;
        else if(val.equalsIgnoreCase("Jun")) return 6;
        else if(val.equalsIgnoreCase("Jul")) return 7;
        else if(val.equalsIgnoreCase("Aug")) return 8;
        else if(val.equalsIgnoreCase("Sep")) return 9;
        else if(val.equalsIgnoreCase("Oct")) return 10;
        else if(val.equalsIgnoreCase("Nov")) return 11;
        else if(val.equalsIgnoreCase("Dec")) return 12;
        return -1;
    }
}
