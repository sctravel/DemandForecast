package com.forecast.demand.common;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

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
            int dbName = config.getInt("database.dbname");
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
        }
        return connection;
    }

}
