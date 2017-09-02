package com.forecast.demand.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

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
    	
    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
    	Server server = new Server();
    	server.setHandler(context);
    	
    	ServerConnector connector = new ServerConnector(server);
        server.setHandler(new HelloHandler());
        connector.setPort(8090);
        server.setConnectors(new Connector[] {connector});
        
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
           jerseyServlet.setInitOrder(0);

           // Tells the Jersey Servlet which REST service/class to load.
           jerseyServlet.setInitParameter(
              "jersey.config.server.provider.classnames",
              DataFeeds.class.getCanonicalName());
       
      
    	try {
			server.start();
			server.join();
    
			
		} catch (Exception e) {
			e.printStackTrace();
			server.destroy();
		}
        System.out.println( "Hello World!" );
    }
}