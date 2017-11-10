package com.forecast.demand.application;

import com.forecast.demand.model.UserView;
import com.forecast.demand.util.DbOperation;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tuxi1 on 11/1/2017.
 */
public class AddNewUserView {
    public static void main(String[] args) throws Exception {

        String username = "admin";
        String tableName = "YumSalesForecast";
        List<String> measures = new ArrayList<>();
        List<String> dimensions = new ArrayList<>();
        String grain = "Daily";
        String dateColumnName = "salesdate";
        String startDate = "2017-07-01";
        String endDate = "2017-07-15";
        Map<String, String> virtualMeasureMap = new HashMap<>();
        boolean isRelative = false;
        int relativeToStart = 0;
        int relativeToEnd = 0

        UserView userView = new UserView(username, tableName, measures, dimensions, grain, dateColumnName,
                startDate, endDate, virtualMeasureMap, isRelative, relativeToStart, relativeToEnd);

        DbOperation.createUserView(username, tableName, userView.toJson());

    }
}
