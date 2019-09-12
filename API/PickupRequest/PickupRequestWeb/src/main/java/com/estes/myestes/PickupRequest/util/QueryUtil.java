package com.estes.myestes.PickupRequest.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Prepare/Generate Different Type of Query for a Database Table
 * @author rahmaat
 *
 */
public class QueryUtil {
	/**
	 * Prepare/Generate Insert Query for a table
	 * @param schema
	 * @param tableName
	 * @param params
	 * @return query
	 */
	public static String prepareInsertQuery(String schema, String tableName, Set<String> params){
		StringBuilder insertQuery = new StringBuilder("INSERT INTO "+schema+"."+tableName+"(");
		
		String columns = params.stream().collect(Collectors.joining(", "));
		
		String values  = columns.replaceAll("\\w+", "?");
		
		return insertQuery.append(columns)
				   .append(" ) VALUES ( ")
				   .append(values)
				   .append(" ) ")
				   .toString();
	}
	
	/**
	 * Prepare update query for a table
	 * @param schema
	 * @param tableName
	 * @param params
	 * @param whereParams
	 * @return query
	 */
	public static String prepareUpdateQuery(String schema, String tableName, Set<String> params,Set<String> whereParams){
		StringBuilder updateQuery = new StringBuilder("UPDATE "+schema+"."+tableName);
		
		updateQuery.append(" SET ");
		
		List<String> columns = params.stream().filter(a->{
			return !whereParams.contains(a);
		}).collect(Collectors.toList());
		
		String updateColumns = columns.stream().map(a->{
			return a+"=?";
		}).collect(Collectors.joining(", "));
		
		
		String whereColumns = whereParams.stream().map(a->{
			return a+"=?";
		}).collect(Collectors.joining(" AND "));

		return updateQuery.append(updateColumns)
				   .append(" WHERE ")
				   .append(whereColumns)
				   .toString();
	}
	
	
	/**
	 * Prepare delete Query for a table
	 * @param schema
	 * @param tableName
	 * @param whereParams
	 * @return query 
	 */
	public static String prepareDeleteQuery(String schema, String tableName, Set<String> whereParams){
		
		StringBuilder deleteQuery = new StringBuilder("DELETE FROM "+schema+"."+tableName);
		
		String whereColumns ="";
		
		if(whereParams!=null){
			whereColumns = whereParams.stream().map(a->{
				return a+"=?";
			}).collect(Collectors.joining(" AND "));
		}
		
		if(whereColumns !=""){
			deleteQuery.append(" WHERE ");
		}
		deleteQuery.append(whereColumns);
		return deleteQuery.toString();
	}
	
	
	/**
	 * Prepare Count Query for a table
	 * @param schema
	 * @param tableName
	 * @param whereParams
	 * @return query
	 */
	
	public static String prepateCountQuery(String schema, String tableName, Set<String> whereParams){
		StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM "+schema+"."+tableName);
		
		String whereColumns ="";
		
		if(whereParams!=null){
			whereColumns = whereParams.stream().map(a->{
				return a+"=?";
			}).collect(Collectors.joining(" AND "));
		}
		
		if(whereColumns !=""){
			countQuery.append(" WHERE ");
		}
		countQuery.append(whereColumns);
		return countQuery.toString();
	}
}
