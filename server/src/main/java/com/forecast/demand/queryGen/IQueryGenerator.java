package com.forecast.demand.queryGen;

import com.forecast.demand.model.Column;
import com.forecast.demand.model.Filter;
import com.forecast.demand.model.Table;

import java.util.List;

public interface IQueryGenerator {
    String generateSelect(List<String> columnNames, boolean isDistinct);
    String generateFrom(String tableName);
    String generateWhere(String filter);
    String generateGroupBy(List<String> columnNames);
    String generateOrderBy(List<String> columnNames, boolean isDesc);
    String generateWhere(List<Filter> filters);
}
