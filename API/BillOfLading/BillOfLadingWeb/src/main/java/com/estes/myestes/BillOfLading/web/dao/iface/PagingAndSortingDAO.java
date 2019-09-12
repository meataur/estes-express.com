package com.estes.myestes.BillOfLading.web.dao.iface;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;

public interface PagingAndSortingDAO<T> {
	
	public Page<T> getResult(String countQuery,String query, List<Object> params, Pageable pageable,RowMapper<T> rowMapper);

}
