package com.estes.myestes.wrinquiry.web.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class WRInquiryRowMapper  implements RowMapper<WRCertificate>{

@Override
public WRCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	WRCertificate wrCertificate= new WRCertificate();
	wrCertificate.setCheckbox(rs.getString(1));
	String proNumber=rs.getString(2)+"-"+rs.getString(3);
	wrCertificate.setProNumber(proNumber);
	wrCertificate.setPoNumber(rs.getString(4));
	wrCertificate.setCorrectionDate(rs.getString(5));
	wrCertificate.setBolNumber(rs.getString(6));
	String correctionType=null;
	switch(rs.getString(7)){    
	case "D":    
		correctionType="Description";
	 break;  
	case "W":    
		correctionType="Weight";  
	 break; 
	case "B":    
		correctionType="Both";
	 break;  
	}
	/*if(rs.getString(8)!=null)
	{
		wrCertificate.setDocumentLink(rs.getString(8));
	}*/
	wrCertificate.setCorrectionType(correctionType);
	return wrCertificate;
}
}
