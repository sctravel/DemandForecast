package com.forecast.demand;

import java.util.List;
import java.util.ArrayList;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.DBLoader;
import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.Table;
import com.forecast.demand.model.ColumnType;

import com.forecast.demand.controller.DataFeeds;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Hello world!
 * navigate to top level folder which contains pom.xml, then run the following command to
 * start maven server
 * mvn clean compile exec:java -e
 * for resource try the example below after jetty server bootup
 * http://localhost:8090/home/test.html
 * for rest end point try example below after jetty server bootup
 * 
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
       
        connector.setPort(8090);
        server.setConnectors(new Connector[] {connector});
                  
        ResourceConfig config = new ResourceConfig();
        config.packages("com.forecast.demand");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        ServletContextHandler restAPIhandler = new ServletContextHandler(server, "/*");
        restAPIhandler.addServlet(servlet, "/*");
       
        ResourceHandler resourceHandler= new ResourceHandler();
        resourceHandler.setResourceBase("public");
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String [] {"test.html"});
        ContextHandler contextStaticContentHandler= new ContextHandler("/home");
        contextStaticContentHandler.setHandler(resourceHandler);
       
        //restAPIhandler,
        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] {contextStaticContentHandler,restAPIhandler});
       
        server.setHandler(handlerCollection);

        ServletHolder staticHolder = new ServletHolder("static-home",new DefaultServlet());
        staticHolder.setInitParameter("dirAllowed","true");
        staticHolder.setInitParameter("pathInfoOnly", "true");
        staticHolder.setInitParameter("resourceBase", "public");
        context.addServlet(staticHolder, "/home/*");

        GlobalCache.initialize();

        try {
            server.start();
            server.join();


        } catch (Exception e) {
            e.printStackTrace();
            server.destroy();
        }

        //Initialize Global Cache for all Table Config
        //XmlHelper.updateTableConfig("resources/YumSalesForecast.xml");
    }
}