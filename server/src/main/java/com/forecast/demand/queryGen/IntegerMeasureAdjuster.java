package com.forecast.demand.queryGen;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.MeasureAdjustment;

import java.util.List;
import java.util.Map;

public class IntegerMeasureAdjuster extends AbstractMeasureAdjuster {
    public void adjustMeasure(MeasureAdjustment adjustment){
        int num = getNumberOfRecordsForDimensionCombo(adjustment.getTableName(), adjustment.getDimensionValues());
        int value = ((int) adjustment.getNewValue())/num;
        int limit = ((int) adjustment.getNewValue()) - value*num;
        //TODO put them in a single transaction?
        String queryNoLimit = buildInsertQuery(adjustment, 0);
        DBHelper.runQuery(queryNoLimit, new String[]{String.valueOf(value)}, new ColumnType[]{ColumnType.INTEGER});
        String queryWithLimit = buildInsertQuery(adjustment, limit);
        DBHelper.runQuery(queryNoLimit, new String[]{String.valueOf(value+1)}, new ColumnType[]{ColumnType.INTEGER});
    }
}
