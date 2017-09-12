package com.forecast.demand.controller;

import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.Table;

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
	@Path("/tables")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTables() {
		System.out.println("Tables");
		List<Table> tables = GlobalCache.getTables();
		return "{getTables}";
	}

	@GET
	@Path("/tables/{tableName}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getColumns(@PathParam("tableName") String tableName) {
		List<Column> columns = GlobalCache.getTable(tableName).getColumns();
	    return "{getColumns}";
	}

	@GET
	@Path("/tables/{tableName}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMeatures(@PathParam("tableName") String tableName) {

	  return "{}";
	}

	@GET
	@Path("/tables/{tableName}/{DimName}/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDistinctValues(@PathParam("tableName")String tableName, @PathParam("DimName") String DimId,
		  @PathParam("filter") String filter) {
	    return "{}";
	}

	@GET
	@Path("/tables/{tableName}/{DimList}/{MeasureList}/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public String query(@PathParam("tableName")String tableName, @PathParam("DimList") List<String> DimList,
		  @PathParam("measureList") List<String> measureList, @PathParam("filter") String filter) {
	  return "{}";
	}

	@POST
	@Path("/tables/{tableName}/{DimList}/{changeList}/{filter}")
	@Produces(MediaType.APPLICATION_JSON)
	public String adjustValue(@PathParam("tableName")String tableName, @PathParam("DimList") List<String> DimList,
		  @PathParam("changeList") List<String> changeList, @PathParam("filter") String filter) {
	return "{}";
	}
}
