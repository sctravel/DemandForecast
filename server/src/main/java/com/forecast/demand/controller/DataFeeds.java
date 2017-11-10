package com.forecast.demand.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.demand.GlobalCache;
import com.forecast.demand.common.DBHelper;
import com.forecast.demand.common.StringUtil;
import com.forecast.demand.model.*;
import com.forecast.demand.queryGen.IMeasureAdjuster;
import com.forecast.demand.queryGen.MeasureAdjusterFactory;
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
	public Response getColumns(@PathParam("tableName") String tableName) throws Exception{
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
	@Path("/tables/{tableName}/userView/{userViewId}/query")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response query(@PathParam("tableName")String tableName, @QueryParam("userViewId") String userViewId,
						  @QueryParam("filter") String filter) throws Exception {
		List<String> columnNames = new ArrayList<>();
		//TODO check userId equals userView.getUserName()
		Table table = GlobalCache.getTable(tableName);
		Map<String, Column> columnMap = table.getColumnMap();

		UserView userView = GlobalCache.getUserView(userViewId);

		TimeGrain timeGrain = userView.getTimeGrain();
		columnNames.add(sqlGen.generateColumnFromGrain(timeGrain, userView.getDateColumnName()) + " AS " + userView.getDateColumnName());
		columnNames.addAll(userView.getDimensions());
		List<String> measureList = userView.getMeasures();
		for(String measure : measureList) {
			if(userView.getVirtualMeasureMap().containsKey(measure)) {
				columnNames.add(columnMap.get(measure).getAggregationType().toString() + "(" + userView.getVirtualMeasureMap().get(measure) + ")");
			} else {
				columnNames.add(columnMap.get(measure).getAggregationType().toString() + "(" + measure + ")");
			}
		}

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(sqlGen.generateSelect(columnNames, false));
		queryBuilder.append(sqlGen.generateFrom(tableName));
		if(filter!=null&&!filter.trim().isEmpty()) queryBuilder.append(sqlGen.generateWhere(filter));

		List<String> dimList = userView.getDimensions();
		dimList.add(sqlGen.generateColumnFromGrain(timeGrain, userView.getDateColumnName()));
		queryBuilder.append(sqlGen.generateGroupBy(dimList));

		String query = queryBuilder.toString();
		List<List<String>> queryResult = DBHelper.getQueryResult(query);
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for(List<String> list : queryResult) {
			int idx = 0;
			Map<String, String> map = new HashMap<>();
			map.put(userView.getDateColumnName(), list.get(idx++));

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

	@GET
	@Path("/tables/{tableName}/query")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response query(@PathParam("tableName")String tableName, @QueryParam("dimList") String dimListString,
		  @QueryParam("measureList") String measureListString, @QueryParam("filter") String filter) throws Exception {
        List<String> columnNames = new ArrayList<>();
        List<String> dimList = Arrays.asList(dimListString.split(","));
		List<String> measureList = Arrays.asList(measureListString.split(","));

		Table table = GlobalCache.getTable(tableName);
		Map<String, Column> columnMap = table.getColumnMap();

        columnNames.addAll(dimList);
        for(String measure : measureList) {
        	columnNames.add( columnMap.get(measure).getAggregationType().toString() +"("+measure+")");
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
	@Path("/tables/{tableName}/adjustValue")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response adjustValue(@PathParam("tableName")String tableName, @QueryParam("measureAdjustment")String measureAdjustment) {
		//IMeasureAdjuster adjuster = MeasureAdjusterFactory.getMeasureAdjuster(measureAdjustment.getMeasureType());
		//adjuster.adjustMeasure(measureAdjustment);
		return Response.ok().build();
	}

	@POST
	@Path("/tables/{tableName}/measures")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response createNewMeasure(@PathParam("tableName")String tableName) {
		//if(measureColumn.getClientExpression()==null||measureColumn.getClientExpression().trim().isEmpty()) {
			//add an extra column in table
			//DBHelper.addColumnToTable(tableName, measureColumn);
		//}
		//add an extra virtual column as expression
		//Table table = GlobalCache.getTable(tableName);
		//table.addColumn(measureColumn);
		//String xml = table.serializeToXml();
		// update xml to DB;

		return Response.ok().build();
	}

	@GET
	@Path("/userViews/{userViewId}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response getUserView(@PathParam("userViewId")String userViewId) {
		// TODO get userId from http body
		// get userview json from db
		String userViewJson = "";
		return Response.ok().entity(userViewJson).build();
	}

	@POST
	@Path("/userViews/")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response CreateOrUpdateUserView(@PathParam("userViewId")String userViewId, @QueryParam("tableName")String tableName, @QueryParam("userViewJson")String userViewJson) {
		// TODO get userId from http body
		// get userview json from db
		return Response.ok().entity(userViewJson).build();
	}

	@DELETE
	@Path("/userViews/{userViewName}")
	@Produces(MediaType.APPLICATION_JSON+"; charset=utf-8")
	public Response deleteUserView(@PathParam("userViewName")String userViewName) {
		// TODO get userId from http body
		// delete userview from db
		String userViewJson = "";
		return Response.ok().entity(userViewJson).build();
	}
}
