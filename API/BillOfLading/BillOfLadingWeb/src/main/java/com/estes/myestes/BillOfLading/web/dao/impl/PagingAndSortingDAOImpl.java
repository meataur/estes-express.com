package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.web.dao.iface.PagingAndSortingDAO;


@Repository("pagingAndSortingDAO")
public class PagingAndSortingDAOImpl <T> implements PagingAndSortingDAO<T> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Page<T> getResult(String countQuery,String query, List<Object> params, Pageable pageable, RowMapper<T> rowMapper){

		
		int page = pageable.getPage();
		if(page < 1){
			throw new BolException(DEFAULT_ERROR_CODE, "Invalid page number. Page number should start with 1","page");
		}

		int totalElements = getTotalResult(countQuery,params);
		
		int size = pageable.getSize();
		
		if(page < 1){
			throw new BolException(DEFAULT_ERROR_CODE, "Invalid page number. Page number should not exceed total page number","page");
		}
		
		int limitStart = (page -1) * size;
		String limitQuery = " LIMIT "+limitStart+" , "+size;
		
		query += limitQuery;
		
		
		
		
		List<T> content = jdbcTemplate.query(query,params.toArray(),rowMapper);
		
		Page<T> result  = new Page<>(content, pageable, totalElements);
		
		return result;
		
	}
	
	private int getTotalResult(String countQuery, List<Object> params){
		return (int) jdbcTemplate.queryForObject(countQuery, params.toArray(), Integer.class);
	}
}
