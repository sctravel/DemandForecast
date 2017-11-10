package com.forecast.demand.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.demand.common.StringUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by tuxi1 on 10/18/2017.
 */
public class UserView {
    private int id;
    private String username;
    private String tableName;
    private List<String> measures;
    private List<String> dimensions;
    private TimeGrain timeGrain;
    private String dateColumnName;

    private String defaultStartDate;
    private String defaultEndDate;

    private Map<String, String> virtualMeasureMap;

    private boolean isRelativeDate;
    private int relativeToStart;
    private int relativeToEnd;

    public UserView(){
        measures = new ArrayList<>();
        dimensions = new ArrayList<>();
        virtualMeasureMap = new HashMap<String, String>();
    }

    public UserView(String username, String tableName, List<String> measures, List<String> dimensions, String grain, String dateColumnName,
                    String startDate, String endDate, Map<String, String> virtualMeasureMap, boolean isRelativeDate, int relativeToStart, int relativeToEnd) {
        this.username = username;
        this.tableName = tableName;
        this.measures = measures;
        this.dimensions = dimensions;
        this.timeGrain = StringUtil.searchEnum(TimeGrain.class, grain);
        this.dateColumnName = dateColumnName;
        this.defaultStartDate =  startDate;
        this.defaultEndDate = endDate;
        this.virtualMeasureMap = virtualMeasureMap;
        this.isRelativeDate = isRelativeDate;
        this.relativeToStart = relativeToStart;
        this.relativeToEnd = relativeToEnd;
    }

    public int getId() {
        return id;
    }
    public String getDateColumnName() {
        return dateColumnName;
    }
    public String getDefaultEndDate() {
        return defaultEndDate;
    }
    public List<String> getDimensions() {
        return dimensions;
    }
    public String getTableName() {
        return tableName;
    }
    public String getUsername() {
        return username;
    }
    public TimeGrain getTimeGrain() {
        return timeGrain;
    }
    public String getDefaultStartDate() {
        return defaultStartDate;
    }
    public List<String> getMeasures() {
        return measures;
    }

    public Map<String, String> getVirtualMeasureMap() {
        return virtualMeasureMap;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVirtualMeasureMap(Map<String, String> virtualMeasureMap) {
        this.virtualMeasureMap = virtualMeasureMap;
    }
    public void setDefaultEndDate(String defaultEndDate) {
        this.defaultEndDate = defaultEndDate;
    }
    public void setDefaultStartDate(String defaultStartDate) {
        this.defaultStartDate = defaultStartDate;
    }
    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }
    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public void setTimeGrain(TimeGrain timeGrain) {
        this.timeGrain = timeGrain;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setDateColumnName(String dateColumnName) {
        this.dateColumnName = dateColumnName;
    }

    public static UserView fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, UserView.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJson(){
        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
