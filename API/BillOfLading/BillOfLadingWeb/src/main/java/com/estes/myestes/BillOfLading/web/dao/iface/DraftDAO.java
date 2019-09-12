package com.estes.myestes.BillOfLading.web.dao.iface;


import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.Draft;

public interface DraftDAO{

	Page<Draft> getDraftList(String username, Pageable pageable, BolFilter filter);

	void saveDraftHeader(String username, BillOfLading billOfLading);

	boolean isDraftExist(String username, String bolNumber);

	void deleteDraftHeader(String username, String bolNumber);

	void deleteDraftReference(String username, String bolNumber);

	void deleteDraftCommodity(String username, String bolNumber);

	void deleteDraftAccessorial(String username, String bolNumber);

	void saveDraftReferences(String username, String bolNumber, BillOfLading billOfLading);

	void saveDraftCommodities(String username, String bolNumber, BillOfLading billOfLading);

	void saveDraftAccessorials(String username, String bolNumber, BillOfLading billOfLading);

	BillOfLading getBillOfLading(String username, String bolNumber);


}
