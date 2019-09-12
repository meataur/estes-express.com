package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.web.dao.sql.FormUtilityQuery.GET_ACCESSORIALS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.BillOfLading.web.dao.iface.FormUtilityDAO;
import com.estes.myestes.BillOfLading.web.dao.mapper.AccessorialMapper;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
@Repository("formUtilityDAO")
public class FormUtilityDAOImpl implements FormUtilityDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Accessorial> getAccessorials() {
		return jdbcTemplate.query(GET_ACCESSORIALS,new AccessorialMapper());
	}
}
