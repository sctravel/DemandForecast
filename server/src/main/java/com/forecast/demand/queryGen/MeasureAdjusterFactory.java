package com.forecast.demand.queryGen;

import com.forecast.demand.model.Column;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.MeasureAdjustment;

import javax.ws.rs.NotSupportedException;

/**
 * Created by tuxi1 on 10/18/2017.
 */
public class MeasureAdjusterFactory {
    public static IMeasureAdjuster getMeasureAdjuster(String measureType) {
        if(measureType.equalsIgnoreCase(ColumnType.INTEGER.toString())) {
            return new IntegerMeasureAdjuster();
        } else if(measureType.equalsIgnoreCase(ColumnType.DECIMAL.toString())) {
            return new DecimalMeasureAdjuster();
        } else {
            throw new NotSupportedException(String.format("Column Type：%s not supported", measureType));
        }
    }
}
