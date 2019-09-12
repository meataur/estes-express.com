package com.estes.services.common.service.impl;


import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.ValidationDAO;
import com.estes.services.common.exception.ValidationException;
import com.estes.services.common.service.iface.ValidationService;




@Service("validationService")
public class ValidationServiceImpl implements ValidationService{
	
	@Autowired
	private ValidationDAO validationDAO;
	
	@Override
	public boolean validateProNumber(String proNumber) {
		
		if(Pattern.matches("\\d{3}-\\d{7}", proNumber)==false){
			throw new ValidationException(ValidationException.DEFAULT_ERROR_CODE, "Invalid Pro Number");
		}

		return validationDAO.validateProNumber(proNumber);
	}


	@Override
	public boolean validateCityStateZip(String city, String state, String zip) {
		return validationDAO.validateCityStateZip(city, state, zip);
	}

}
