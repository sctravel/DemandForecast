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

    private Date defaultStartDate;
    private Date defaultEndDate;

    public UserView(){

    }

    public static UserView fromJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, UserView.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    public String toJson() throws Exception  {
        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return value;
    }
}
