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
    private static Map<String, Map<String, String>> userViewCache = new HashMap<>(); // userId - Map<userViewName, json>

    public static void initialize() {
        tableCache = DbOperation.getTableMap();
        userViewCache = DbOperation.getUserViewMap();
    }

    public static void addToUserViewCache(String userId, String userViewName, String userViewJson) {
        if(userViewCache.containsKey(userId)) {
            userViewCache.get(userId).put(userViewName, userViewJson);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(userViewName, userViewJson);
        }
    }

    public static Table getTable(String tableName) throws UserException {
        if(tableCache.containsKey(tableName)) {
            return tableCache.get(tableName);
        } else {
            throw new UserException(String.format("tableName - {%s} doesn't exist", tableName));
        }
    }

    public static Map<String, String> getUserViewMap(String userId) throws UserException {
        if(userViewCache.containsKey(userId)) {
            return userViewCache.get(userId);
        } else {
            return new HashMap<>();
        }
    }

    public static List<Table> getTables() {
        return new ArrayList<>(tableCache.values());
    }
}
