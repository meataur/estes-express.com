package com.estes.myestes.BillOfLading.web.service.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Bol;
import com.estes.myestes.BillOfLading.web.dto.BolDocument;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.BolResponse;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;

public interface BolService {
	
	public Bol getBolById(String username, int bolId);

	public Page<Bol> getBolHistory(String username, Pageable pageable, BolFilter filter);

	public BillOfLading getBillOfLadingById(String username, AuthenticationDetails auth, int id);

	public void cancelBol(String username, String accessToken,  int id);

	public BolDocument createBolLabelDocument(String username, int id, ShippingLabel shippingLabel);

	public BolDocument getBolDocument(String username, int id);

	public BolDocument getBolLabelDocument(String username, int id);

	public BolResponse  createBol(AuthenticationDetails auth, BillOfLading billOfLading, String updateType,
			String templateName);

}
