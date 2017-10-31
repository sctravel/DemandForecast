package com.forecast.demand.application;

import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBLoader;

/**
 * Created by tuxi1 on 10/30/2017.
 */
public class LoadCsvToTable {

    public static void main(String[] args) throws Exception{
        if (args == null || args.length == 0) {
            GlobalCache.initialize();
            DBLoader loader = new DBLoader();
            loader.loadDataFromFile("db/YumDemoData.csv", ",", "YumSalesForecast", GlobalCache.getTable("YumSalesForecast").getColumns());
        } else {
            String tableName = args[0];
            String filePath = args[1];
            GlobalCache.initialize();
            DBLoader loader = new DBLoader();
            loader.loadDataFromFile(filePath, ",", tableName, GlobalCache.getTable(tableName).getColumns());
        }
    }
}
