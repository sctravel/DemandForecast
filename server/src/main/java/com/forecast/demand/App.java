package com.forecast.demand;


import com.forecast.demand.common.DBLoader;

/**
 * Hello world!
 * navigate to top level folder which contains pom.xml, then run the following command to
 * start maven server
 * mvn clean compile exec:java -e
 */
public class App 
{
    public static void main( String[] args )
    {
    	/*
    	jettyServer server = new jettyServer();
    	try {
			server.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println( "Hello World!" );*/
		DBLoader loader = new DBLoader("","");
		loader.loadDataFromFile("C:\\GitHub\\DemandForecast\\server\\db\\Yum.csv", ",","",null);
    }
}