package com.forecast.demand.util;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.UserView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuxi1 on 10/23/2017.
 */
public class DbOperation {
    public static void createUserView(String userId, String tableName, String userViewJson) {
        String sql = "";
    }

    public static void updateUserView(int userViewId, String userId, String tableName, String userViewJson) {
        String sql = "Update UserViews set details = ? where userId = ? and tableName = ? and id = ?" ;
    }

    public static List<UserView> getUserViews(String userId, String tableName) {
        List<UserView> userViewList = new ArrayList<>();
        String sql = "select details from UserViews where userId = ? and tableName = ?";

        //DBHelper.getQueryResult()

        return userViewList;
    }
}
