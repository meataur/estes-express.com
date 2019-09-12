package com.estes.myestes.ImageViewing.web.dao.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.myestes.ImageViewing.exception.AppException;
import com.estes.myestes.ImageViewing.web.dao.iface.WeightResearchInquiryDAO;
import com.estes.myestes.ImageViewing.web.dto.WRCertificate;
import com.estes.myestes.ImageViewing.web.dto.WREmailRequest;
import com.estes.myestes.ImageViewing.web.dto.WRInquiryRowMapper;
import com.estes.myestes.ImageViewing.web.dto.WRSearchRequest;
 

@Repository("weightResearchInquiryDAO")
public class WeightResearchInquiryDAOImpl implements WeightResearchInquiryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String[] formatProNumber(String proNumber){
		proNumber = proNumber.replaceAll("\\D+","");
		proNumber = String.format("%4s", proNumber).replace(' ', '0');
		proNumber = proNumber.substring(0, 3)+"-"+proNumber.substring(3);
		return proNumber.split("-");
		
	}
	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName) {
		String query = null;
		if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("PRO")) {
			query = "select distinct 'checkbox'," + "DIGITS(frp1.fhot)," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP, '' "
					+ "from fbfiles.frp001  frp1 " + "INNER JOIN fbfiles.wrp002 wrp on " + "frp1.fhot=wrp.whot "
					+ "and frp1.fhpro=wrp.whpro " + "and WHNAMT-whoamt >0 ";
			StringBuffer proNumber1 = new StringBuffer();
			StringBuffer proNumber2 = new StringBuffer();
			
			for (int i = 0; i < wrSearchRequest.getSearchTerm().size(); i++) {
				
				
				String pro[] = formatProNumber(wrSearchRequest.getSearchTerm().get(i));
				
				proNumber1 = proNumber1.append("'" + pro[0] + "'");
				proNumber2 = proNumber2.append("'" + pro[1] + "'");
				
				if (i < wrSearchRequest.getSearchTerm().size() - 1) {
					proNumber1.append(",");
					proNumber2.append(",");
				}
				
				
			}
			query = query + "and frp1.fhot in (" + proNumber1.toString() + ")  "
							+ "and frp1.fhpro in ("+ proNumber2.toString() + ") "
							+ "and (fhscd = '" + wrSearchRequest.getAccountNumber() + "' "
							+ "OR fhccd = '" + wrSearchRequest.getAccountNumber() + "' "
							+ "OR fhbtc = '" + wrSearchRequest.getAccountNumber() + "')";
		} else if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("Other")) {
			query = "select distinct 'checkbox'," + "frp1.fhot," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP, '' " 
					+ "from fbfiles.frp001  frp1 " + "INNER JOIN  fbfiles.wrp002 wrp on frp1.fhot=wrp.whot "
					+ "INNER JOIN fbfiles.frp103 frp03 on frp1.fhot=frp03.mxot " + "and frp1.fhpro=frp03.mxpro "
					+ "and frp1.fhpro=wrp.whpro " + "and WHNAMT-whoamt >0 ";
			StringBuffer other = new StringBuffer();
			for (int i = 0; i < wrSearchRequest.getSearchTerm().size(); i++) {
				String searchTerm = wrSearchRequest.getSearchTerm().get(i);
				other = other.append("'" + searchTerm + "'");
				if (i < wrSearchRequest.getSearchTerm().size() - 1) {
					other.append(",");
				}
			}
			query = query + " and frp03.mxxtr in (" + other.toString() + ")"
					+ "and (fhscd = '" + wrSearchRequest.getAccountNumber() + "' "
					+ "OR fhccd = '" + wrSearchRequest.getAccountNumber() + "' "
					+ "OR fhbtc = '" + wrSearchRequest.getAccountNumber() + "')";
		} else if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("Date Range")) {
			query = "select distinct 'checkbox'," + "DIGITS(frp1.fhot)," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP, '' "
					+ "from fbfiles.frp001  frp1 " + "INNER JOIN fbfiles.wrp002 wrp on " + "frp1.fhot=wrp.whot "
					+ "and frp1.fhpro=wrp.whpro " + "and WHNAMT-whoamt >0 ";
			query = query + "AND  whcdt8  >='" + wrSearchRequest.getStartDate() + "'" + " AND whcdt8  <='"
					+ wrSearchRequest.getEndDate() + "'" 
					+ " AND (fhscd = '" + wrSearchRequest.getAccountNumber() + "'"
					+ "OR fhccd = '" + wrSearchRequest.getAccountNumber() + "'" 
					+ "OR fhbtc = '" + wrSearchRequest.getAccountNumber() + "')";
		}
		List<WRCertificate> certificateData = jdbcTemplate.query(query, new WRInquiryRowMapper());
		return certificateData;
	}
	
	public List<WRCertificate> getDocumentDeails(List<String> proNumbers,String userName) {		
		String query = "select distinct 'checkbox'," + "wrot," + "wrpro," + "frp1.fhpo," + "whcdt8," + "fhbl# as fhbl, "
				+ "WHCTYP,  "
				+ "wrloc "
				+ "from fbfiles.frp001  frp1 " + "INNER JOIN  fbfiles.wrp002 wrp on frp1.fhot=wrp.whot "
				+ " INNER JOIN  fbfiles.wrp210 wrp2 on " + "frp1.fhot=wrp2.wrot " + "and frp1.fhpro=wrp.whpro "
				+ "and frp1.fhpro=wrp2.wrpro ";
		StringBuffer requestNumbers = new StringBuffer();
		int index = 1;
		for (String proNumber : proNumbers) {
			String requestNumber = generateRequestNumber();
			requestNumbers = requestNumbers.append("'").append(requestNumber).append("'");
			if (index < proNumbers.size()) {
				requestNumbers = requestNumbers.append(",");
			}
			writeRequestToFile(proNumber, requestNumber,userName);
			callViewDocumentProcess(requestNumber,userName);
			index++;
		}
		query = query + " and wruser='"+userName+"' and wrref in (" + requestNumbers.toString() + ")";
		List<WRCertificate> certificateData = jdbcTemplate.query(query, new WRInquiryRowMapper());

		return certificateData;
	}

	private void writeRequestToFile(String proNumber, String requestNumber,String userName) {
		String insertSql = "INSERT INTO fbfiles.wrp210 (wrot,wrpro,wrref,wruser) VALUES (?, ?, ?, ?)";
		String pro[] = proNumber.split("-");
		String proNumber1 = pro[0].trim();
		String proNumber2 = pro[1].trim();
		Object[] params = new Object[] { proNumber1, proNumber2, requestNumber, userName };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		jdbcTemplate.update(insertSql, params, types);
	}

	private void callViewDocumentProcess(String requestNumber,String userName) {

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("ref", requestNumber);
		inParamMap.put("user", userName);
		inParamMap.put("email", "");
		inParamMap.put("check", "");

		SqlParameterSource inParams = new MapSqlParameterSource(inParamMap);

		SimpleJdbcCall viewDocumentProceure = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withSchemaName("FBPGMS")
				.withProcedureName("WRQ2101");
		viewDocumentProceure.execute(inParams);
	}


	private synchronized String generateRequestNumber() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmssSS");

		return sdf.format(gc.getTime()).substring(0, 14);
	}

	public String getEmail(WREmailRequest wrEmailRequest,String userName) {	
		
		
		String message=null;				
		
		if(wrEmailRequest.getEmailAddresses().size() > 0) {
		   for(String emailAddress : wrEmailRequest.getEmailAddresses()) {
				 if(isEmailValid(emailAddress)) {	
					 
					 String requestNumber = generateRequestNumber();
					 
					 List<String> proNumbers = wrEmailRequest.getProNumbers();
					 for (String proNumber : proNumbers) {
						
						writeRequestToFile(proNumber, requestNumber,userName);
					 }
					 
					 callPrintDocumentProcess(emailAddress,userName,requestNumber);
					 message = "Your request has been processed. You should receive an e-mail shortly";
				 }
				 else {
					 throw new AppException("One or more of the e-mail addresses entered are not valid"); 
				 }
			  }
		}
		else {
			throw new AppException("Please enter at least one e-mail address");
		}
		return message;
	}
	
	private void callPrintDocumentProcess(String emailAddress, String userName, String requestNumber) {
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("ref", requestNumber);
		inParamMap.put("user", userName);
		inParamMap.put("email",emailAddress);
		inParamMap.put("check","");

		SqlParameterSource inParams = new MapSqlParameterSource(inParamMap);

		SimpleJdbcCall printDocumentProcedure = new SimpleJdbcCall(jdbcTemplate.getDataSource())
				.withSchemaName("FBPGMS").withProcedureName("WRQ2102");

		printDocumentProcedure.execute(inParams);
		
	}
	private boolean isEmailValid(String email) { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
}
