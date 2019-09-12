package com.estes.myestes.wrinquiry.web.dao.impl;

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

import com.estes.myestes.wrinquiry.web.dao.iface.WRInquiryDAO;
import com.estes.myestes.wrinquiry.web.dto.WRCertificate;
import com.estes.myestes.wrinquiry.web.dto.WREmailRequest;
import com.estes.myestes.wrinquiry.web.dto.WRInquiryRowMapper;
import com.estes.myestes.wrinquiry.web.dto.WRSearchRequest;

//import com.estesexpress.www.toolbox.html.error.Validation;

@Repository("wrinquiryDAO")
public class WRInquiryDAOImpl implements WRInquiryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<WRCertificate> searchWRInquiryDetails(WRSearchRequest wrSearchRequest,String userName) {
		String query = null;

		if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("PRO")) {
			query = "select distinct 'checkbox'," + "frp1.fhot," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP " +
					"from fbfiles.frp001  frp1 " + "INNER JOIN fbfiles.wrp002 wrp on " + "frp1.fhot=wrp.whot "
					+ "and frp1.fhpro=wrp.whpro " + "and WHNAMT-whoamt >0 ";
			StringBuffer proNumber1 = new StringBuffer();
			StringBuffer proNumber2 = new StringBuffer();
			for (int i = 0; i < wrSearchRequest.getSearchTerm().size(); i++) {

				String pro[] = wrSearchRequest.getSearchTerm().get(i).split("-");
				proNumber1 = proNumber1.append("'" + pro[0] + "'");
				proNumber2 = proNumber2.append("'" + pro[1] + "'");
				if (i < wrSearchRequest.getSearchTerm().size() - 1) {
					proNumber1.append(",");
					proNumber2.append(",");
				}

			}
			query = query + "and frp1.fhot in (" + proNumber1.toString() + ")  and frp1.fhpro in ("
					+ proNumber2.toString() + ")";
		} else if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("OTHER")) {
			query = "select distinct 'checkbox'," + "frp1.fhot," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP " +
					"from fbfiles.frp001  frp1 " + "INNER JOIN  fbfiles.wrp002 wrp on frp1.fhot=wrp.whot "
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
			query = query + " and frp03.mxxtr in (" + other.toString() + ")";
		} else if (wrSearchRequest != null && wrSearchRequest.getSearchBy().equals("Date Range")) {
			query = "select distinct 'checkbox'," + "frp1.fhot," + "frp1.fhpro," + "frp1.fhpo," + "whcdt8,"
					+ "fhbl# as fhbl, " + "WHCTYP " +
					"from fbfiles.frp001  frp1 " + "INNER JOIN fbfiles.wrp002 wrp on " + "frp1.fhot=wrp.whot "
					+ "and frp1.fhpro=wrp.whpro " + "and WHNAMT-whoamt >0 ";
			query = query + "AND  whcdt8  >='" + getFormattedDate(wrSearchRequest.getStartDate()) + "'" + " AND whcdt8  <='"
					+ getFormattedDate(wrSearchRequest.getEndDate()) + "'" + " AND (fhscd = '" + wrSearchRequest.getAccountNumber() + "'"
					+ "OR fhccd = '" + wrSearchRequest.getAccountNumber() + "'" + "OR fhbtc = '"
					+ wrSearchRequest.getAccountNumber() + "')";
		}

		List<WRCertificate> certificateData = jdbcTemplate.query(query, new WRInquiryRowMapper());

		return certificateData;
	}

	public List<WRCertificate> getDocumentDeails(List<WRCertificate> wrCertificates,String userName) {
		
		String query = "select distinct 'checkbox'," + "wrot," + "wrpro," + "frp1.fhpo," + "whcdt8," + "fhbl# as fhbl, "
				+ "WHCTYP,  "
				+ "'<a target=\"_blank\" href=\"wr.pdf?location=' || wrloc || '\">View Document</a>' AS wrloc "
				+ "from fbfiles.frp001  frp1 " + "INNER JOIN  fbfiles.wrp002 wrp on frp1.fhot=wrp.whot "
				+ " INNER JOIN  fbfiles.wrp210 wrp2 on " + "frp1.fhot=wrp2.wrot " + "and frp1.fhpro=wrp.whpro "
				+ "and frp1.fhpro=wrp2.wrpro ";

		StringBuffer requestNumbers = new StringBuffer();
		int index = 1;

		for (WRCertificate wrCertificate : wrCertificates) {
			String requestNumber = generateRequestNumber();
			requestNumbers = requestNumbers.append("'").append(requestNumber).append("'");
			if (index < wrCertificates.size()) {
				requestNumbers = requestNumbers.append(",");
			}
			writeRequestToFile(wrCertificate, requestNumber,userName);
			callViewDocumentProcess(requestNumber,userName);
			index++;
		}
		query = query + " and wruser='"+userName+"' and wrref in (" + requestNumbers.toString() + ")";

		List<WRCertificate> certificateData = jdbcTemplate.query(query, new WRInquiryRowMapper());

		return certificateData;
	}

	private void writeRequestToFile(WRCertificate wrCertificates, String requestNumber,String userName) {

		String insertSql = "INSERT INTO fbfiles.wrp210 (wrot,wrpro,wrref,wruser) VALUES (?, ?, ?, ?)";

		String pro[] = wrCertificates.getProNumber().split("-");
		String proNumber1 = pro[0];
		String proNumber2 = pro[1];

		Object[] params = new Object[] { proNumber1, proNumber2, requestNumber, userName };

		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };

		int row = jdbcTemplate.update(insertSql, params, types);
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
		Map result = viewDocumentProceure.execute(inParams);
	}

	private void callPrintDocumentProcess(String emailAddres,String userName) {

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("ref", generateRequestNumber());
		inParamMap.put("user", userName);
		inParamMap.put("email",emailAddres);
		inParamMap.put("check","");

		SqlParameterSource inParams = new MapSqlParameterSource(inParamMap);

		SimpleJdbcCall printDocumentProcedure = new SimpleJdbcCall(jdbcTemplate.getDataSource())
				.withSchemaName("FBPGMS").withProcedureName("WRQ2102");

		Map result = printDocumentProcedure.execute(inParams);
	}

	private synchronized String generateRequestNumber() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmssSS");

		return sdf.format(gc.getTime()).substring(0, 14);
	}

	public String getEmail(List<WREmailRequest> wrEmailRequests,String userName) {
		
		String message=null;
		
		for(WREmailRequest wrEmailRequest:wrEmailRequests)
		{
			
			List<WRCertificate> certificates=getDocumentDeails(wrEmailRequest.getWrCertificates(),userName);
			
			if(wrEmailRequest.getEmailAddresses()!=null)
			{
					for(String emailAddress:wrEmailRequest.getEmailAddresses())
					{
					 if (emailAddress != null && emailAddress.length()>0) {
						 if(isEmailValid(emailAddress))
						 {
							 callPrintDocumentProcess(emailAddress,userName);
							 message = "Your request has been processed.<br/>You should receive an e-mail shortly";
						 }
						 else
						 {
							 message = "One or more of the e-mail addresses entered are not valid"; 
						 }
						}
					}
			}
			else
			{
				message = "Please enter at least one e-mail address";
			}
		}
		return message;
	}
	
	private boolean isEmailValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
	
	private String getFormattedDate(String dateStr) {
		String formattedDate = null;
		formattedDate = dateStr.substring(6)+dateStr.substring(0,2)+dateStr.substring(3,5);
		return formattedDate;
		
		
	}

}
