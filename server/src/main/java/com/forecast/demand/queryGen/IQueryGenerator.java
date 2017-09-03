package com.forecast.demand.queryGen;

import com.forecast.demand.model.Column;
import com.forecast.demand.model.Filter;
import com.forecast.demand.model.Table;

import java.util.List;

public interface IQueryGenerator {
    String generateSelect(List<Column> columns, boolean isDistinct);
    String generateFrom(Table table);
    String generateWhere(List<Filter> filters);
}
