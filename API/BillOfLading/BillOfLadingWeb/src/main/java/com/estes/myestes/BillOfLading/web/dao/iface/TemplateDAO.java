package com.estes.myestes.BillOfLading.web.dao.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;

import com.estes.myestes.BillOfLading.web.dto.TemeplateFilter;
import com.estes.myestes.BillOfLading.web.dto.Template;

public interface TemplateDAO{

	Page<Template> getTemplatetList(String username, Pageable pageable, TemeplateFilter filter);

	void saveTemplateHeader(String username, String template, BillOfLading bol);

	boolean isTemplateExist(String username, String template);

	void saveTemplateReferences(String username, String template, BillOfLading billOfLading);

	void saveTemplateCommodities(String username, String template, BillOfLading billOfLading);

	void saveTemplateAccessorials(String username, String template, BillOfLading billOfLading);
	
	

	void deleteTemplateHeader(String username, String template);
	void deleteTemplateReference(String username, String template);

	void deleteTemplateCommodity(String username, String template);

	void deleteTemplateAccessorial(String username, String template);

	BillOfLading getBillOfLading(String username, String templateName);

}
