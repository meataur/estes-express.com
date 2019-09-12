package com.estes.myestes.BillOfLading.web.dao.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Bol;
import com.estes.myestes.BillOfLading.web.dto.BolDocument;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;

public interface BolDAO{

	Bol getBolById(String username, int bolId);

	Page<Bol> getBolHistory(String username, Pageable pageable, BolFilter filter);

	void saveBolHeader(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName);

	void saveCommodities(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName);

	void saveReferences(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName);

	void saveAccessorials(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName);

	String writeFinalBol(String randomNumber, String timestamp);

	BillOfLading getBillOfLadingById(String username, int bolId);

	void cancelBol(String username, int id);

	BolDocument createBolLabelDocument(String username, int id, ShippingLabel shippingLabel);

	BolDocument getBolLabelDocument(String username, int id);

	BolDocument getBolDocument(String username, int id);

	boolean isBolExist(String username, int bolId);

	void saveServiceLevel(String serviceLevel, String username, int bolId);

}
