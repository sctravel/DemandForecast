package com.forecast.demand.queryGen;

import com.forecast.demand.model.MeasureAdjustment;

public interface IMeasureAdjuster {
    void adjustMeasure(MeasureAdjustment adjustment);
}
