package com.forecast.demand.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("data")
public class DataFeeds {
	
	  @GET
	  @Path("/table")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getTables() {
	    return "{}";
	  }
	  
	  @GET
	  @Path("/table/columns/{tableName}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getColumns(@PathParam("tableName") String tableName) {
	    return "{}";
	  }
	  
	  @GET
	  @Path("/table/measures/{tableName}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getMeatures(@PathParam("tableName") String tableName) {
	    return "{}";
	  }
	  
	  @GET
	  @Path("/table/{tableName}/{DimName}/{filter}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getDistinctValues(@PathParam("tableName")String tableName, @PathParam("DimName") String DimId,
			  @PathParam("filter") String filter) {
	    return "{}";
	  }
	  
	  @GET
	  @Path("/table/query/{tableName}/{DimList}/{MeasureList}/{filter}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String query(@PathParam("tableName")String tableName, @PathParam("DimList") List<String> DimList,
			  @PathParam("measureList") List<String> measureList, @PathParam("filter") String filter) {
	    return "{}";
	  }
	  
	  @POST
	  @Path("/table/adjust/{tableName}/{DimList}/{changeList}/{filter}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String adjustValue(@PathParam("tableName")String tableName, @PathParam("DimList") List<String> DimList,
			  @PathParam("changeList") List<String> changeList, @PathParam("filter") String filter) {
	    return "{}";
	  }


}
