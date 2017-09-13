package com.forecast.demand.queryGen;

import com.forecast.demand.model.Filter;

import java.util.List;

public class SQLQueryGenerator implements IQueryGenerator{

    public String generateSelect(List<String> columnNames, boolean isDistinct) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        if(isDistinct) sb.append(" DISTINCT ");
        for(String name : columnNames) {
            sb.append("\n " + name + ",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ");
        return sb.toString();
    }

    public String generateFrom(String tableName){
        return "\n FROM " + tableName;
    }

    public String generateWhere(String filter) {
        return "\n Where " + filter;
    }

    public String generateWhere(List<Filter> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n WHERE ");
        boolean first = true;
        for(Filter f : filters) {
            if(first) {
                first = false;
            } else {
                sb.append(" AND ");
            }
            //sb.append(f.getColumnName() + f.getOperation().toString() + f.getValue());
        }
        return sb.toString();
    }
}
