package com.forecast.demand;

import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.Table;

import java.util.*;

public class GlobalCache {
    private static Map<String, Table> tableCache = new HashMap<>();

    public static synchronized Table getTable(String tableName) {
        if(tableCache.containsKey(tableName)) {
            return tableCache.get(tableName);
        } else {
            Table table = XmlHelper.readTableFromXmlConfig("Resources/" + tableName + ".xml");
            tableCache.put(tableName, table);
            return table;
        }
    }

    public static List<Table> getTables() {
        //TODO add implementation
        return new ArrayList<>(tableCache.values());
    }
}
