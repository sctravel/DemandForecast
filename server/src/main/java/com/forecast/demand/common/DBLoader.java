package com.forecast.demand.common;

import java.time.LocalDate;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.ColumnType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by tuxi1 on 9/1/2017.
 */
public class DBLoader {

    public DBLoader() { }

    private String generateInsertStatement(String tableName, List<Column> columnList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
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
        String val = null;
        if(index<content.length) val=content[index];
        try {
            if(column.getType()==ColumnType.STRING) {
                preparedStmt.setString (index+1, val);
            } else if(column.getType()==ColumnType.INTEGER) {
                preparedStmt.setInt(index+1, val==null||val.isEmpty()? 0 :Integer.parseInt(val));
            } else if(column.getType()==ColumnType.BOOLEAN) {
                preparedStmt.setBoolean(index+1, val==null||val.isEmpty() ? false : Boolean.valueOf(val));
            } else if(column.getType()==ColumnType.DATETIME) {
                LocalDate date = val==null||val.isEmpty() ? null : DateTimeUtil.parseDateIndMMMyy(val, "-");
                preparedStmt.setDate(index+1, date==null? java.sql.Date.valueOf("1900-01-01") :java.sql.Date.valueOf(date));
            } else if(column.getType()==ColumnType.DECIMAL) {
                preparedStmt.setDouble(index+1, val==null||val.isEmpty()? 0 :Double.parseDouble(val));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loadDataFromFile(String filePath, String delimiter, String tableName, List<Column> columnList) {

        BufferedReader br = null;
        String line = "";
        Connection connection = null;
        PreparedStatement preparedStmt=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            connection = DBHelper.getConnection();
            connection.setAutoCommit(false);
            String query = generateInsertStatement(tableName, columnList);
            preparedStmt = connection.prepareStatement(query);
            int count = 0;
            while ((line = br.readLine()) != null) {
                ++count;
                preparedStmt.clearParameters();
                // use comma as separator
                String[] content = line.split(delimiter);
                int index = 0;
                for(Column c : columnList) {
                    setColumnValue(preparedStmt, c, content, index++);
                }
                // execute the preparedstatement
                preparedStmt.execute();
            }
            System.out.println("Total rows processed: " + count);
            connection.commit();
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
            if(preparedStmt!=null) {
                try {
                    preparedStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
