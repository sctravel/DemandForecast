package com.forecast.demand;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.Table;

import java.util.*;

public class GlobalCache {
    private static Map<String, Table> tableCache = new HashMap<>();
    private static Map<String, String> userViewCache = new HashMap<>();

    public static void initialize() {
        String query = "Select tablename, xmlconfig, owners, description from TableMetadata";
        List<List<String>> res = DBHelper.getQueryResult(query);
        for(List<String> list : res) {
            String tableName = list.get(0);
            String xmlConfig = list.get(1);
            Table table = XmlHelper.readTableFromXmlConfig(xmlConfig);
            tableCache.put(tableName, table);
        }
    }

    public static Table getTable(String tableName) {
        if(tableCache.containsKey(tableName)) {
            return tableCache.get(tableName);
        } else {
            //TODO refine this logic, if not found in DB, search in the resources folder for xml file
            Table table = XmlHelper.readTableFromXmlConfigFile("Resources/" + tableName + ".xml");
            if(table!=null) tableCache.put(tableName, table);
            return table;
        }
    }

    public static List<Table> getTables() {
        return new ArrayList<>(tableCache.values());
    }
}
