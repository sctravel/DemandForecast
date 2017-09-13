package com.forecast.demand.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.Table;
import com.forecast.demand.queryGen.SQLQueryGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("data")
public class DataFeeds {
	private SQLQueryGenerator sqlGen = new SQLQueryGenerator();

	@GET
	@Path("/tables")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String getTables() {
		List<Table> tables = GlobalCache.getTables();
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(tables);
			System.out.println("JSON - " +tables.size() + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@GET
	@Path("/tables/{tableName}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String getColumns(@PathParam("tableName") String tableName) {
		Table table = GlobalCache.getTable(tableName);
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(table);
			System.out.println("JSON - " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@GET
	@Path("/tables/{tableName}/distinctValues")
    @Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String getDistinctValues(@PathParam("tableName")String tableName, @QueryParam("dimName") List<String> DimList,
		  @QueryParam("filter") String filter) {
        List<String> columnNames = new ArrayList<>();
        columnNames.addAll(DimList);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(sqlGen.generateSelect(columnNames, true));
        queryBuilder.append(sqlGen.generateFrom(tableName));
        if(filter!=null&&!filter.trim().isEmpty()) queryBuilder.append(sqlGen.generateWhere(filter));
        List<List<String>> res = DBHelper.getQueryResult(queryBuilder.toString());

        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(res);
            System.out.println("JSON - " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
	}

	@GET
	@Path("/tables/{tableName}/query")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String query(@PathParam("tableName")String tableName, @QueryParam("dimList") List<String> DimList,
		  @QueryParam("measureList") List<String> measureList, @QueryParam("filter") String filter) {
        List<String> columnNames = new ArrayList<>();
        columnNames.addAll(DimList);
        columnNames.addAll(measureList);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(sqlGen.generateSelect(columnNames, false));
        queryBuilder.append(sqlGen.generateFrom(tableName));
        if(filter!=null&&!filter.trim().isEmpty()) queryBuilder.append(sqlGen.generateWhere(filter));
        String query = queryBuilder.toString();
        System.out.println("Query - " + query);
        List<List<String>> res = DBHelper.getQueryResult(query);

        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(res);
            System.out.println("JSON - " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
	}

	@POST
	@Path("/tables/{tableName}/{DimList}/{changeList}/{filter}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String adjustValue(@PathParam("tableName")String tableName, @PathParam("DimList") List<String> DimList,
		  @PathParam("changeList") List<String> changeList, @PathParam("filter") String filter) {
	return "{}";
	}
}
