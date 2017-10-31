package com.forecast.demand.application;

import com.forecast.demand.common.XmlHelper;

import java.io.File;

/**
 * Created by tuxi1 on 10/30/2017.
 */
public class AddNewTableConfiguration {
    public static void main(String[] args) throws Exception {
        if(args!=null&&args.length==1) {
            String tableName = args[0];
            System.out.println("Updating Table: " + tableName);
            XmlHelper.addNewTableConfig("resources/"+tableName+".xml");
        } else {
            throw new Exception("Args length should be 1. You should provide tableName");
        }
    }
}
