package com.forecast.demand;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.Table;
import com.forecast.demand.model.UserView;
import com.forecast.demand.util.DbOperation;
import com.forecast.demand.exception.UserException;

import java.util.*;

public class GlobalCache {
    private static Map<String, Table> tableCache = new HashMap<>();
    private static Map<String, UserView> userViewCache = new HashMap<>();

    public static void initialize() {
        tableCache = DbOperation.getTableMap();
        //userViewCache = DbOperation.getUserViewMap();
    }

    public static Table getTable(String tableName) throws UserException {
        if(tableCache.containsKey(tableName)) {
            return tableCache.get(tableName);
        } else {
            throw new UserException(String.format("tableName - {%s} doesn't exist", tableName));
        }
    }

    public static UserView getUserView(String userViewId) throws UserException {
        if(userViewCache.containsKey(userViewId)) {
            return userViewCache.get(userViewId);
        } else {
            throw new UserException(String.format("userViewId - {%s} doesn't exist", userViewId));
        }
    }

    public static List<Table> getTables() {
        return new ArrayList<>(tableCache.values());
    }
}
