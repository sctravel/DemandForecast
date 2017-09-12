package com.forecast.demand.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static List<List<String>> getQueryResult(String query) {
        //String query = "SELECT table_name FROM information_schema.tables where table_schema='Inventory'";
        List<List<String>> result = new ArrayList<List<String>>();
        Connection connection = DBUtil.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while ( rs.next() ) {
                List<String> list = new ArrayList<>();
                int numColumns = rs.getMetaData().getColumnCount();
                for ( int i = 1 ; i <= numColumns ; i++ ) {
                    // Column numbers start at 1.
                    // Also there are many methods on the result set to return
                    //  the column as a particular type. Refer to the Sun documentation
                    //  for the list of valid conversions.
                    list.add(rs.getString(i));
                    System.out.println( "COLUMN " + i + " = " + rs.getObject(i) );
                }
                result.add(list);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(rs!=null) {
                try {rs.close();} catch (Exception e) {e.printStackTrace();}
            }
            if(stmt!=null) {
                try {stmt.close();} catch (Exception e) {e.printStackTrace();}
            }
            if(connection!=null) {
                try {connection.close();} catch (Exception e) {e.printStackTrace();}
            }
        }
        return result;
    }
}
