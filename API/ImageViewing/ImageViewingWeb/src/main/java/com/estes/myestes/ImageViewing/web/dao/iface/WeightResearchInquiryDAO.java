package com.estes.myestes.ImageViewing.web.dao.iface;

import java.util.List;

import com.estes.myestes.ImageViewing.web.dto.WRCertificate;
import com.estes.myestes.ImageViewing.web.dto.WREmailRequest;
import com.estes.myestes.ImageViewing.web.dto.WRSearchRequest;

public interface WeightResearchInquiryDAO {
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName);
	
	public List<WRCertificate> getDocumentDeails(List<String> proNumbers,String userName);
	
	public String getEmail(WREmailRequest wrEmailRequest,String userName);

}
