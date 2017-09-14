package com.forecast.demand.queryGen;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.MeasureAdjustment;

import java.util.List;
import java.util.Map;

public abstract class AbstractMeasureAdjuster implements IMeasureAdjuster {

    protected int getNumberOfRecordsForDimensionCombo(String tableName, Map<String, String> dimensionValues) {
        int result = 0;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT COUNT(1) FROM ");
        queryBuilder.append(tableName);
        queryBuilder.append(" WHERE ");
        int index = 0;
        for(Map.Entry<String, String> entry : dimensionValues.entrySet()) {
            if(index++>0) queryBuilder.append(" AND ");
            queryBuilder.append(entry.getKey() + "=\"" + entry.getValue()+"\"");
        }
        List<List<String>> res = DBHelper.getQueryResult(queryBuilder.toString());
        return Integer.parseInt(res.get(0).get(0));
    }

    protected String buildInsertQuery(MeasureAdjustment adjustment, int limit) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" UPDATE " + adjustment.getTableName());
        queryBuilder.append("\n SET " + adjustment.getMeasureName() + " = ? \n WHERE ");
        int index = 0;
        for(Map.Entry<String, String> entry : adjustment.getDimensionValues().entrySet()) {
            if(index++>0) queryBuilder.append("\n AND ");
            queryBuilder.append(entry.getKey() + "=\"" + entry.getValue()+"\"");
        }
        if(limit>0) queryBuilder.append("\n LIMIT " + limit);
        return queryBuilder.toString();
    }

}
