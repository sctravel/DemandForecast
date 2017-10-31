package com.forecast.demand.util;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.XmlHelper;
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

    public static void createUserView(String userId, String tableName, String userViewJson) {
        String sql = "";
    }

    public static void updateUserView(int userViewId, String userId, String tableName, String userViewJson) {
        String sql = "Update UserViews set details = ? where userId = ? and tableName = ? and id = ?" ;
    }

    public static Map<String, UserView> getUserViewMap() {
        Map<String, UserView> map = new HashMap<>();
        String query = "select id, details, tableName, userId from UserViews";
        List<List<String>> res = DBHelper.getQueryResult(query);
        for(List<String> list : res) {
            String userViewId = list.get(0);
            String userViewJson = list.get(1);
            UserView userView = UserView.fromJson(userViewJson);
            if(userView!=null)
                map.put(userViewId, userView);
        }
        return map;
    }
}
