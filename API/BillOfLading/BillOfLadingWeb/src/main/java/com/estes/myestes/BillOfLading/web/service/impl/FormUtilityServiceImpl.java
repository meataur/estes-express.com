package com.estes.myestes.BillOfLading.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.BillOfLading.web.dao.iface.FormUtilityDAO;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.service.iface.FormUtilityService;

@Service("formUtilityService")
public class FormUtilityServiceImpl  implements FormUtilityService{
	
	@Autowired
	private FormUtilityDAO formUtilityDAO;
	
	@Override
	public List<Accessorial> getAccessorials() {
		return formUtilityDAO.getAccessorials();
	}
}
