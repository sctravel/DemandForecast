package com.forecast.demand.server;


/**
 * Hello world!
 *
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