package com.forecast.demand.queryGen;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.MeasureAdjustment;

import java.util.List;
import java.util.Map;

public class DecimalMeasureAdjuster extends AbstractMeasureAdjuster {

    public void adjustMeasure(MeasureAdjustment adjustment){
        int num = getNumberOfRecordsForDimensionCombo(adjustment.getTableName(), adjustment.getDimensionValues());
        double value = adjustment.getNewValue() / num;
        String query = buildInsertQuery(adjustment, 0);
        DBHelper.runQuery(query, new String[] {String.valueOf(value)}, new ColumnType[]{ColumnType.DECIMAL});
    }
}
