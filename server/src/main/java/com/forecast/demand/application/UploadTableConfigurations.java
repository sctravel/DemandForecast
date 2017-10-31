package com.forecast.demand.application;

import java.io.File;
import com.forecast.demand.common.XmlHelper;
/**
 * Created by tuxi1 on 10/13/2017.
 */
public class UploadTableConfigurations {

    // if no args, default is to update all configurations in resources folder
    // if arges length==1, args[0] is name of the Table to be updated
    public static void main(String[] args) {
        if(args==null||args.length==0) {
            XmlHelper.updateTableConfig("resources/YumSalesForecast.xml");
        } else {
            String tableName = args[0];
            System.out.println("Updating Table: " + tableName);
            XmlHelper.updateTableConfig("resources/"+tableName+".xml");
        }
    }
}
