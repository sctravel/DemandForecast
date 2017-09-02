package com.forecast.demand.common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.ColumnType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tuxi1 on 9/1/2017.
 */
public class DBLoader {
    private String connectionString;
    private String schemaName;

    public DBLoader(String connectionString, String schemaName) {
        this.connectionString = connectionString;
        this.schemaName = schemaName;
    }

    private String generateInsertStatement(String tableName, List<Column> columnList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        List<String> columnNames = new ArrayList<String>();
        sb.append(" ( ");
        for(Column c : columnList){
            sb.append(c.getName());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ) VALUES ( ");
        for(Column c : columnList){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ) ");
        return sb.toString();
    }

    private void setColumnValue(PreparedStatement preparedStmt, Column column, String[] content, int index) {
        if(index>=content.length) return;
        if(column.getType()==ColumnType.String) {

        } else if (column.getType()==ColumnType.Integer) {

        }
    }

    public boolean loadDataFromFile(String filePath, String delimiter, String tableName, List<Column> columnList) {
        BufferedReader br = null;
        String line = "";
        Connection connection = null;
        ResultSet rs = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            connection = getConnection();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] content = line.split(delimiter);
                String query = generateInsertStatement(tableName, columnList);
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                int index = 0;
                for(Column c : columnList) {
                    setColumnValue(preparedStmt, c, content, index++);
                }
                preparedStmt.setString (1, "Barney");
                preparedStmt.setString (2, "Rubble");
                preparedStmt.setDate   (3, startDate);
                preparedStmt.setBoolean(4, false);
                preparedStmt.setInt    (5, 5000);

                // execute the preparedstatement
                preparedStmt.execute();
                System.out.println("Country [code= " + content[0] + " , name=" + content[5] + "]");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    private Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            String driverName = "com.mysql.jdbc.Driver"; // MySQL MM JDBC driver
            Class.forName(driverName);

            // Create a connection to the database
            String serverName = "demandforecast.cbcwdscsgece.us-west-2.rds.amazonaws.com:3306";
            String mydatabase = "Inventory";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // a JDBC url
            String username = "admin";
            String password = "Test1234";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
            e.printStackTrace();
        } catch (SQLException e) {
            // Could not connect to the database
            e.printStackTrace();
        }
        return connection;
    }
    //private
}
