package com.forecast.demand;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.DBLoader;
import com.forecast.demand.common.XmlHelper;
import com.forecast.demand.model.Table;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.controller.DataFeeds;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
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
import org.eclipse.jetty.util.security.Constraint;
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
 * username:admin
 * password:test
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
        resourceHandler.setWelcomeFiles(new String [] {"createUserView.html","logon.html"});
        ContextHandler contextStaticContentHandler= new ContextHandler("/home");
        contextStaticContentHandler.setHandler(resourceHandler); 
           
        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] {contextStaticContentHandler,restAPIhandler});

        ServletHolder staticHolder = new ServletHolder("static-home",new DefaultServlet());
        staticHolder.setInitParameter("dirAllowed","true");
        staticHolder.setInitParameter("pathInfoOnly", "true");
        staticHolder.setInitParameter("resourceBase", "public");
        context.addServlet(staticHolder, "/home/*");
        
        LoginService loginService = new HashLoginService("MyRealm",
                "resources/realm.properties");
        server.addBean(loginService);
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        
        // SecurityHandler security = basicAuth(); 
        
        
        server.setHandler(security);
        
        security.setHandler(handlerCollection);
        
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[] { "user", "admin" });

        // Binds a url pattern with the previously created constraint. The roles
        // for this constraing mapping are mined from the Constraint itself
        // although methods exist to declare and bind roles separately as well.
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        // First you see the constraint mapping being applied to the handler as
        // a singleton list, however you can passing in as many security
        // constraint mappings as you like so long as they follow the mapping
        // requirements of the servlet api. Next we set a BasicAuthenticator
        // instance which is the object that actually checks the credentials
        // followed by the LoginService which is the store of known users, etc.
        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);
        
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
    
    private static final SecurityHandler basicAuth() throws IOException {    
        File jdbcRealmFile = new File("jdbc-realm.properties");
        LoginService l = new JDBCLoginService("jdbc", jdbcRealmFile.getAbsolutePath());
        
        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(new String[]{"user"});
        constraint.setAuthenticate(true);
         
        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/*");
        
        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
        csh.setAuthenticator(new BasicAuthenticator());
        csh.setRealmName("myrealm");
        csh.addConstraintMapping(cm);
        csh.setLoginService(l);
        
        return csh;
    	
    }
}