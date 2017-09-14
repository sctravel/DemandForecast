package com.forecast.demand.common;

import com.forecast.demand.model.ColumnType;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            String driverName = "com.mysql.jdbc.Driver"; // MySQL MM JDBC driver
            Class.forName(driverName);

            Configurations configs = new Configurations();
            Configuration config = configs.properties(new File("config/forecastdemand.config"));
            // access configuration properties
            String dbHost = config.getString("database.host");
            int dbPort = config.getInt("database.port");
            String dbName = config.getString("database.dbname");
            String dbUser = config.getString("database.user");
            String dbPassword = config.getString("database.password", "secret");  // provide a default
            long dbTimeout = config.getLong("database.timeout");
            // Create a connection to the database
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8&connectTimeout="+dbTimeout; // a JDBC url
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        }   catch (ConfigurationException cex){
            cex.printStackTrace();
        }   catch (ClassNotFoundException e) {
            // Could not find the database driver
            e.printStackTrace();
        } catch (SQLException e) {
            // Could not connect to the database
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void runQuery(String query, String[] values, ColumnType[] types) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        try {
            connection = getConnection();
            preparedStmt = connection.prepareStatement(query);

            int index = 0;
            System.out.println("values length - " + values.length);
            while(index<values.length) {
                if(types[index]==ColumnType.STRING) {
                    preparedStmt.setString (index+1, values[index]);
                } else if(types[index]==ColumnType.INTEGER) {
                    preparedStmt.setInt(index+1, Integer.parseInt(values[index]));
                } else if(types[index]==ColumnType.BOOLEAN) {
                    preparedStmt.setBoolean(index+1, Boolean.valueOf(values[index]));
                } else if(types[index]==ColumnType.DATETIME) {
                    LocalDate date = LocalDate.parse(values[index], DateTimeFormatter.BASIC_ISO_DATE);
                    preparedStmt.setDate(index+1, date==null? java.sql.Date.valueOf("0000-01-01") :java.sql.Date.valueOf(date));
                } else if(types[index]==ColumnType.DECIMAL) {
                    preparedStmt.setDouble(index+1,Double.parseDouble(values[index]));
                } else {
                    preparedStmt.setString (index+1, values[index]);
                }
                ++index;
            }

            // execute the preparedstatement
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    }

    public static List<List<String>> getQueryResult(String query) {
        //String query = "SELECT table_name FROM information_schema.tables where table_schema='Inventory'";
        List<List<String>> result = new ArrayList<List<String>>();
        if(query==null||query.isEmpty()) return result;
        System.out.println("Query - " + query);
        Connection connection = getConnection();
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
                    list.add(new String(rs.getBytes(i),"UTF-8"));
                }
                result.add(list);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
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
