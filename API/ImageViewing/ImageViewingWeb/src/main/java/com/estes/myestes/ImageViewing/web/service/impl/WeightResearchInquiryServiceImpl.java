package com.estes.myestes.ImageViewing.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.ImageViewing.web.dao.iface.WeightResearchInquiryDAO;
import com.estes.myestes.ImageViewing.web.dto.WRCertificate;
import com.estes.myestes.ImageViewing.web.dto.WREmailRequest;
import com.estes.myestes.ImageViewing.web.dto.WRSearchRequest;
import com.estes.myestes.ImageViewing.web.service.iface.WeightResearchInquiryService;

@Service("weightResearchInquiryService")
public class WeightResearchInquiryServiceImpl implements WeightResearchInquiryService{
	
	@Autowired
	private WeightResearchInquiryDAO weightResearchInquiryDAO;
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName)
	{
		return weightResearchInquiryDAO.searchWRInquiryDetails(wrSearchRequest,userName);
	}
	
	public List<WRCertificate> getDocumentDeails(List<String> proNumbers,String userName)
	{
		return weightResearchInquiryDAO.getDocumentDeails(proNumbers,userName);
	}
	
	public String getEmail(WREmailRequest wrEmailRequest,String userName)
	{
		return weightResearchInquiryDAO.getEmail(wrEmailRequest,userName);
	}
}
