package com.estes.myestes.BillOfLading.web.dao.iface;

import java.util.List;

public interface CommonDAO {

	int getTotalResult(String countQuery, List<Object> params);

	int getTotalResult(String countQuery, Object... params);

	boolean hasExist(String countQuery, List<Object> params);

	boolean hasExist(String countQuery, Object... params);

}
