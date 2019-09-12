package com.estes.myestes.ImageViewing.web.service.iface;

import java.util.List;

import com.estes.myestes.ImageViewing.web.dto.WRCertificate;
import com.estes.myestes.ImageViewing.web.dto.WREmailRequest;
import com.estes.myestes.ImageViewing.web.dto.WRSearchRequest;

public interface WeightResearchInquiryService {
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String username);
	
	public List<WRCertificate> getDocumentDeails(List<String> proNumbers,String username);
	
	public String getEmail(WREmailRequest wrEmailRequest,String userName);

}
