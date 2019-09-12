package com.estes.myestes.wrinquiry.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.wrinquiry.web.dao.iface.WRInquiryDAO;
import com.estes.myestes.wrinquiry.web.dto.WRCertificate;
import com.estes.myestes.wrinquiry.web.dto.WREmailRequest;
import com.estes.myestes.wrinquiry.web.dto.WRSearchRequest;
import com.estes.myestes.wrinquiry.web.service.iface.WRInquiryService;

@Service("wrinquiryservice")
public class WRInquiryServiceImpl implements WRInquiryService{
	
	@Autowired
	private WRInquiryDAO wrInquiryDAO;
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName)
	{
		return wrInquiryDAO.searchWRInquiryDetails(wrSearchRequest,userName);
	}
	
	public List<WRCertificate> getDocumentDeails(List<WRCertificate> wrCertificates,String userName)
	{
		return wrInquiryDAO.getDocumentDeails(wrCertificates,userName);
	}
	
	public String getEmail(List<WREmailRequest> wrEmailRequests,String userName)
	{
		return wrInquiryDAO.getEmail(wrEmailRequests,userName);
	}


}
