package com.estes.myestes.wrinquiry.web.dao.iface;

import java.util.List;

import com.estes.myestes.wrinquiry.web.dto.WRCertificate;
import com.estes.myestes.wrinquiry.web.dto.WREmailRequest;
import com.estes.myestes.wrinquiry.web.dto.WRSearchRequest;

public interface WRInquiryDAO {
	
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName);
	
	public List<WRCertificate> getDocumentDeails(List<WRCertificate> wrCertificates,String userName);
	
	public String getEmail(List<WREmailRequest> wrEmailRequests,String userName);

}
