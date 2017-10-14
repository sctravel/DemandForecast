package com.forecast.demand.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBHelper;
import com.forecast.demand.model.Column;
import com.forecast.demand.model.Table;
import com.forecast.demand.queryGen.SQLQueryGenerator;
import com.forecast.demand.queryGen.IQueryGenerator;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("data")
public class DataFeeds {
	private IQueryGenerator sqlGen = new SQLQueryGenerator();

	@GET
	@Path("/tables")
    @Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response getTables() {
		List<Table> tables = GlobalCache.getTables();
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(tables);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok().entity(value).build();
	}

	@GET
	@Path("/tables/{tableName}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response getColumns(@PathParam("tableName") String tableName) {
		Table table = GlobalCache.getTable(tableName);
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(table);
		} catch (Exception e) {
			e.printStackTrace();
            return Response.serverError().build();
        }
		return Response.ok().entity(value).build();
	}

	@GET
	@Path("/tables/{tableName}/distinctValues")
    @Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response getDistinctValues(@PathParam("tableName")String tableName, @QueryParam("dimList") String dimListString,@QueryParam("dimType") String dimType,@QueryParam("dimLevel") String dimLevel,@QueryParam("Levels") String Levels,
		  @QueryParam("filter") String filter) {
        List<String> columnNames = new ArrayList<>();
		List<String> dimList = Arrays.asList(dimListString.split(","));
        columnNames.addAll(dimList);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(sqlGen.generateSelect(columnNames, true));
        queryBuilder.append(sqlGen.generateFrom(tableName));
        if(filter!=null&&!filter.trim().isEmpty()) queryBuilder.append(sqlGen.generateWhere(filter));
        List<List<String>> res = DBHelper.getQueryResult(queryBuilder.toString());
        List<String> dt = new LinkedList<String>() ;
		List<String> dl = new LinkedList<String>() ;
		List<String> le = new LinkedList<String>() ;

		le.add(Levels);
		dl.add(dimLevel);
        dt.add(dimType);
		res.add(le);
		res.add(dl);
        res.add(dt);

        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(res);
            System.out.println(value);


        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().entity(value).build();
	}

	@GET
	@Path("/tables/{tableName}/query")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response query(@PathParam("tableName")String tableName, @QueryParam("dimList") String dimListString,
		  @QueryParam("measureList") String measureListString, @QueryParam("filter") String filter) {
        List<String> columnNames = new ArrayList<>();
        List<String> dimList = Arrays.asList(dimListString.split(","));
		List<String> measureList = Arrays.asList(measureListString.split(","));

        columnNames.addAll(dimList);
        //TODO add feature to specify the aggregator (e.g. SUM, AVG, etc.) by user
        for(String measure : measureList) {
        	columnNames.add("SUM("+measure+")");
		}
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(sqlGen.generateSelect(columnNames, false));
        queryBuilder.append(sqlGen.generateFrom(tableName));
        if(filter!=null&&!filter.trim().isEmpty()) queryBuilder.append(sqlGen.generateWhere(filter));
		queryBuilder.append(sqlGen.generateGroupBy(dimList));

        String query = queryBuilder.toString();
        List<List<String>> queryResult = DBHelper.getQueryResult(query);
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for(List<String> list : queryResult) {
			int idx = 0;
			Map<String, String> map = new HashMap<>();
			for(String dim : dimList) {
				map.put(dim, list.get(idx++));
			}
			for(String measure : measureList) {
				map.put(measure, list.get(idx++));
			}
			res.add(map);
		}

        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(res);
            System.out.println("JSON - " + value);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().entity(value).build();
	}

	@POST
	@Path("/tables/{tableName}/{DimList}/{changeList}/{filter}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public String adjustValue(@PathParam("tableName")String tableName, @PathParam("dimList") String dimListSrting,
		  @PathParam("changeList") String changeListString, @PathParam("filter") String filter) {
	    return "{}";
	}
}
