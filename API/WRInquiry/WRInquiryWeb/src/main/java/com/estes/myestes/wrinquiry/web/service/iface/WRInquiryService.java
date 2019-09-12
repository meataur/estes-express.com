package com.estes.myestes.wrinquiry.web.service.iface;

import java.util.List;

import com.estes.myestes.wrinquiry.web.dto.WRCertificate;
import com.estes.myestes.wrinquiry.web.dto.WREmailRequest;
import com.estes.myestes.wrinquiry.web.dto.WRSearchRequest;

public interface WRInquiryService {
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String username);
	
	public List<WRCertificate> getDocumentDeails(List<WRCertificate> wrCertificates,String username);
	
	public String getEmail(List<WREmailRequest> wrEmailRequests,String userName);

}
