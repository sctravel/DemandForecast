package com.forecast.demand;

import java.util.List;
import java.util.ArrayList;

import com.forecast.demand.common.DBLoader;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.ColumnType;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

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
    	
    	Server server = new Server();
    	ServerConnector connector = new ServerConnector(server);
       
        connector.setPort(8090);
        server.setConnectors(new Connector[] {connector});
           
       ResourceConfig config = new ResourceConfig();
       config.packages("com.forecast.demand.server");
       ServletHolder servlet = new ServletHolder(new ServletContainer(config));
       ServletContextHandler context = new ServletContextHandler(server, "/*");
       context.addServlet(servlet, "/*");
       
		DBLoader loader = new DBLoader();
		List<Column> columnList = new ArrayList<Column>();
		columnList.add(new Column("District", ColumnType.STRING));
		columnList.add(new Column("Province", ColumnType.STRING));
		columnList.add(new Column("City", ColumnType.STRING));
		columnList.add(new Column("DistributionCenter", ColumnType.STRING));
		columnList.add(new Column("Item", ColumnType.STRING));
		columnList.add(new Column("Category", ColumnType.STRING));
		columnList.add(new Column("Subcategory", ColumnType.STRING));
		columnList.add(new Column("DetailCategory", ColumnType.STRING));
		columnList.add(new Column("SalesType", ColumnType.STRING));
		columnList.add(new Column("SalesDate", ColumnType.DATETIME));
		columnList.add(new Column("HistoricalSalesNumber", ColumnType.INTEGER));
		columnList.add(new Column("ForecastSalesNumber", ColumnType.INTEGER));
		columnList.add(new Column("MarketingAdjustment", ColumnType.INTEGER));
		columnList.add(new Column("DemandPlannerAdjustment", ColumnType.INTEGER));
		columnList.add(new Column("ConsensusForecast", ColumnType.INTEGER));



//		loader.loadDataFromFile("/Users/XiTU/javaWorkSpace/DemandForecast/server/db/Yum.csv", ",", "YumSalesForecast", columnList);
       System.out.println( "Hello World!" );
      
    	try {
			server.start();
			server.join();
    
			
		} catch (Exception e) {
			e.printStackTrace();
			server.destroy();
		}
    	
    	

    }
}