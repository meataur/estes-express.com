package com.estes.myestes.ImageViewing.web.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WRInquiryRowMapper  implements RowMapper<WRCertificate>{

	@Override
	public WRCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		WRCertificate wrCertificate= new WRCertificate();
		wrCertificate.setCheckbox(rs.getString(1));
		String proNumber= rs.getString(2)+"-"+rs.getString(3);
		wrCertificate.setProNumber(proNumber);
		wrCertificate.setPoNumber(rs.getString(4).trim());
		wrCertificate.setCorrectionDate(rs.getString(5));
		wrCertificate.setBolNumber(rs.getString(6).trim());
		String correctionType=null;
		switch(rs.getString(7)) {    
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
		wrCertificate.setCorrectionType(correctionType);
		if(rs.getString(8)!=null && !rs.getString(8).equals("")) {
			wrCertificate.setDocumentLink(rs.getString(8).trim());
		}
		return wrCertificate;
	}
}
