package com.forecast.demand.model;

import com.forecast.demand.common.StringUtil;

/**
 * Created by tuxi1 on 10/14/2017.
 */
public class MeasureColumn extends Column {

    private boolean isEditable;
    private AggregationType aggregationType;
    private String clientExpression;

    public MeasureColumn(String name, String type, boolean isNullable, String displayName, boolean isMeasure, boolean isEditable, String aggregationType, String clientExpression) {
        super(name, type, isNullable, displayName, isMeasure, isEditable, aggregationType, clientExpression);
        this.isEditable = isEditable;
        this.aggregationType = StringUtil.searchEnum(AggregationType.class, aggregationType);
        this.clientExpression = clientExpression;
    }

    public boolean getIsEditable() { return this.isEditable; }

    public AggregationType getAggregationType() { return this.aggregationType; }

    public String getClientExpression() { return clientExpression; }
}
