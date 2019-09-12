package com.estes.myestes.BillOfLading.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.BillOfLading.web.dao.iface.CommonDAO;

@Repository("commonDAO")
public class CommonDAOImpl implements CommonDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public  int getTotalResult(String countQuery, List<Object> params){
		return (int) jdbcTemplate.queryForObject(countQuery, params.toArray(), Integer.class);
	}
	
	@Override
	public  int getTotalResult(String countQuery, Object... params){
		return (int) jdbcTemplate.queryForObject(countQuery, params, Integer.class);
	}
	
	@Override
	public boolean hasExist(String countQuery, List<Object> params){
		int count = getTotalResult(countQuery, params);
		return count >0;
	}
	
	@Override
	public boolean hasExist(String countQuery, Object... params){
		int count = getTotalResult(countQuery, params);
		return count >0;
	}
}
