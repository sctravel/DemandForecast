package com.forecast.demand.queryGen;

import com.forecast.demand.model.Column;
import com.forecast.demand.model.Filter;
import com.forecast.demand.model.Table;

import java.util.List;

public class SQLQueryGenerator implements IQueryGenerator{

    public String generateSelect(List<Column> columns, boolean isDistinct) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        if(isDistinct) sb.append(" DISTINCT ");
        for(Column c : columns) {
            sb.append("\n " + c.getName() + ",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ");
        return sb.toString();
    }

    public String generateFrom(Table table){
        return "\n FROM " + table.getName();
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
            sb.append(f.getColumnName() + f.getOperation().toString() + f.getValue());
        }
        return sb.toString();
    }
}
