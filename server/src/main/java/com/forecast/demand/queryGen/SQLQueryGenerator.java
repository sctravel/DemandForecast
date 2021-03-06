package com.forecast.demand.queryGen;

import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.Filter;
import com.forecast.demand.model.FilterOperation;
import com.forecast.demand.model.TimeGrain;

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
            if(f.getValues()==null||f.getValues().isEmpty()) continue;
            if(first) {
                first = false;
            } else {
                sb.append(" AND ");
            }
            sb.append(f.getColumnName() + getOperator(f.getOperation()) + getValue(f.getValues(),f.getOperation(),f.getColumnType()));
        }
        return sb.toString();
    }

    public String generateGroupBy(List<String> columnNames) {
        if(columnNames==null||columnNames.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(" GROUP BY ");
        for(String name : columnNames) {
            sb.append("\n " + name + ",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ");
        return sb.toString();
    }

    //May need Pair<ColumnName, isDesc> in future.
    public String generateOrderBy(List<String> columnNames, boolean isDesc) {
        if(columnNames==null||columnNames.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(" Order BY ");
        for(String name : columnNames) {
            sb.append("\n " + name);
            if(isDesc) sb.append(" DESC ");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ");
        return sb.toString();
    }

    public String generateColumnFromGrain(TimeGrain grain, String column) {
        StringBuilder sb = new StringBuilder();
        switch (grain) {
            case DAILY:
                sb.append(column);
                break;
            case WEEKLY:
                //DATE_ADD(salesdate, INTERVAL(1-DAYOFWEEK(salesdate)) DAY) AS salesdate
                sb.append("DATE_ADD("+column+", INTERVAL(1-DAYOFWEEK("+column+ ")) DAY) ");
                break;
            case MONTHLY:
                //salesdate-dayofmonth(salesdate)+1 AS salesdate
                sb.append(column+"-dayofmonth("+column+")+1 ");
                break;
            case QUARTERLY:
                //MAKEDATE(YEAR(salesdate), 1) + INTERVAL QUARTER(salesdate) QUARTER - INTERVAL 1 QUARTER
                sb.append("MAKEDATE(year("+column+"),1) + INTERVAL QUARTER("+column+") QUARTER - INTERVAL 1 QUARTER ");
                break;
            case YEARLY:
                //MAKEDATE(year(salesdate),1) AS salesdate
                sb.append("MAKEDATE(year("+column+"),1) ");
                break;
            default:
                sb.append(column);
                break;
        }
        return sb.toString();
    }

    private String getValue(String value, ColumnType type) {
        if(type==ColumnType.DATETIME || type==ColumnType.STRING) {
            return "'"+value+"'";
        }
        return value;
    }

    private String getValue(List<String> values, FilterOperation oper, ColumnType type) {
        if(values==null||values.isEmpty()) return "";
        if(oper==FilterOperation.IN) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for(String v : values) {
                sb.append(getValue(v, type));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            return sb.toString();
        } else {
            return getValue(values.get(0), type);
        }
    }

    private String getOperator(FilterOperation oper) {
        switch (oper) {
            case EQUAL: return "=";
            case LESS: return "<";
            case LESSEQUAL: return "<=";
            case GREATER: return ">";
            case GREATEREQUAL: return ">=";
            case NOTEQUAL: return "!=";
            case IN: return "IN";
            default:
                break;
        }
        return null;
    }
}
