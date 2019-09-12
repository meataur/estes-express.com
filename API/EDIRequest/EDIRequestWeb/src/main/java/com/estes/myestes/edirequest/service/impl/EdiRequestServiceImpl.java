/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.edirequest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.edirequest.dao.iface.EdiRequestDAO;
import com.estes.myestes.edirequest.dto.BillingHeaderType;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.exception.EdiRequestException;
import com.estes.myestes.edirequest.service.iface.EdiRequestService;
import com.estes.myestes.edirequest.utils.EdiRequestConstant;
import com.estes.myestes.edirequest.utils.EdiRequestUtil;

@Service("ediRequestService")
@Scope("prototype")
public class EdiRequestServiceImpl implements EdiRequestService, EdiRequestConstant
{
	@Autowired
	private EdiRequestDAO ediRequestDAO;

	@Override
	public List<BillingHeaderType> getEdiBillingTypes() throws EdiRequestException {
		return ediRequestDAO.getEdiBillingTypes();
	}

	@Override
	public List<BillingHeaderType> getEdiHeaderTypes() throws EdiRequestException {
		return ediRequestDAO.getEdiHeaderTypes();
	}

	@Override
	public String saveEdiFormTransmissionRequest(EdiRequest ediRequest) throws EdiRequestException {
		return ediRequestDAO.saveEdiFormTransmissionRequest(ediRequest);
	}
	
	@Override
	public List<ServiceResponse> validateRequest(EdiRequest ediRequest) {
		List<ServiceResponse> respList = new ArrayList<ServiceResponse>();	
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getName())){	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.name");
			resp.setMessage("Name is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getAddress())){	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.address");
			resp.setMessage("Address is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getCity())){	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.city");
			resp.setMessage("City is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getState())) {	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.state");
			resp.setMessage("State is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getZip())) {	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.zip");
			resp.setMessage("Zip Code is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getCustomer().getPhone())) {	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("customer.phone");
			resp.setMessage("Phone is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getPrimaryEdiContact().getName())) {
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("primaryEdiContact.name");
			resp.setMessage("Name is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getPrimaryEdiContact().getPhone())) {	
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("primaryEdiContact.phone");
			resp.setMessage("Phone is a required entry field.");
			respList.add(resp);
		}
		if(EdiRequestUtil.isNullOrEmpty(ediRequest.getPrimaryEdiContact().getEmail())) {
			ServiceResponse resp = new ServiceResponse("","");
			resp.setErrorCode("Error");
			resp.setFieldName("primaryEdiContact.email");
			resp.setMessage("Email is a required entry field.");
			respList.add(resp);
		} 
		return respList;
	}
}
