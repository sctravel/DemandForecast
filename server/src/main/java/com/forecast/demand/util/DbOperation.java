package com.forecast.demand.util;

import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.Table;
import com.forecast.demand.model.UserView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tuxi1 on 10/23/2017.
 */
public class DbOperation {

    public static Map<String, Table> getTableMap() {
        Map<String, Table> map = new HashMap<>();
        String query = "Select tablename, xmlconfig, owners, description from TableMetadata";
        List<List<String>> res = DBHelper.getQueryResult(query);
        for(List<String> list : res) {
            String tableName = list.get(0);
            String xmlConfig = list.get(1);
            Table table = XmlHelper.readTableFromXmlConfig(xmlConfig);
            map.put(tableName, table);
        }
        return map;
    }

    public static void deleteUserView(String userId, String userViewName) {
        String sql = "Delete From UserViews where userId = ? and userViewName = ?";
        DBHelper.runQuery(sql, new String[]{userId, userViewName}, new ColumnType[]{ColumnType.INTEGER, ColumnType.STRING});
    }

    public static void createUserView(String userId, String userViewName, String userViewJson) {
        System.out.println("user view Json: " + userViewJson);

        String sql = "Insert into UserViews (userViewName, userId, details) Values (?, ?, ?)";
        DBHelper.runQuery(sql, new String[]{userViewName, userId, userViewJson},
                new ColumnType[]{ColumnType.STRING, ColumnType.INTEGER, ColumnType.STRING, ColumnType.STRING});

    }

    public static void updateUserView(String userId, String userViewName, String userViewJson) {
        String sql = "Update UserViews set details = ? where userId = ? and userViewName = ?" ;
        DBHelper.runQuery(sql, new String[]{userViewJson, userId, userViewName}, new ColumnType[] {ColumnType.STRING, ColumnType.INTEGER, ColumnType.STRING});
    }

    public static Map<String, Map<String, String>> getUserViewMap() {
        Map<String, Map<String, String>> map = new HashMap<>();
        String query = "select id, userId, userViewName, details from UserViews";
        List<List<String>> res = DBHelper.getQueryResult(query);
        for(List<String> list : res) {
            String userViewId = list.get(0);
            String userId = list.get(1);
            String userViewName = list.get(2);
            String userViewJson = list.get(3);
            if(map.containsKey(userId)) {
                Map<String, String> nameMap = map.get(userId);
                nameMap.put(userViewName, userViewJson);
            } else {
                Map<String, String> nameMap = new HashMap<>();
                map.put(userId, nameMap);
                nameMap.put(userViewName, userViewJson);
            }
        }
        return map;
    }
}
