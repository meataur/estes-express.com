package com.estes.myestes.BillOfLading.web.service.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.Draft;

public interface DraftService {

	public Page<Draft> getDraftList(String username, Pageable pageable, BolFilter filter);

	public BillOfLading getDraftByBolNumber(String username, AuthenticationDetails auth, String bolNumber);

	public void deleteDraft(String username, String accessToken,  String bolNumber);

	public void createDraft(String username, AuthenticationDetails auth, BillOfLading billOfLading);

}
