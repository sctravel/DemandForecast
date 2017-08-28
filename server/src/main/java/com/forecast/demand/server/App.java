package com.forecast.demand.server;


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
    	jettyServer server = new jettyServer();
    	try {
			server.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println( "Hello World!" );
    }
}