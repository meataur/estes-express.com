package com.estes.myestes.BillOfLading.web.service.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.TemeplateFilter;
import com.estes.myestes.BillOfLading.web.dto.Template;

public interface TemplateService {

	public Page<Template> getTemplates(String username, Pageable pageable, TemeplateFilter filter);
	
	public void createTemplate(String username, AuthenticationDetails auth, String template, BillOfLading billOfLading);

	public BillOfLading getTemplateByTemplateName(String username, AuthenticationDetails auth, String template);

	public void deleteTemplate(String username, String accessToken, String template);

}
