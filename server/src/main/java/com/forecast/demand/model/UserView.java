package com.forecast.demand.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tuxi1 on 10/18/2017.
 */
public class UserView {
    private String username;
    private String tableName;
    private List<String> measures;
    private List<String> dimensions;
    private TimeGrain timeGrain;
    private String dateColumnName;

    private Date defaultStartDate;
    private Date defaultEndDate;

    public String getDateColumnName() {
        return dateColumnName;
    }
    public Date getDefaultEndDate() {
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
    public Date getDefaultStartDate() {
        return defaultStartDate;
    }
    public List<String> getMeasures() {
        return measures;
    }

    public void setDefaultEndDate(Date defaultEndDate) {
        this.defaultEndDate = defaultEndDate;
    }
    public void setDefaultStartDate(Date defaultStartDate) {
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
