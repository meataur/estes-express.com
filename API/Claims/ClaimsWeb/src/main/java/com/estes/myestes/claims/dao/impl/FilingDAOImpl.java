package com.estes.myestes.claims.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.edps.format.StringFormat;
import com.estes.dto.common.Address;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dao.iface.FilingDAO;
import com.estes.myestes.claims.dto.ClaimantResponseDTO;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.util.ClaimsConstant;
import com.estes.myestes.claims.util.ClaimsFormFileItems;
import com.estes.myestes.claims.util.PhoneUtil;

@Repository ("filingDAO")
public class FilingDAOImpl implements FilingDAO {

	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public ProInfoResponseDTO getProInfo(String ot, String pro) throws ClaimsException {
		String sql = "select fhsnm, fhsa1, fhsa2, fhsct, fhsst, fhszip, fhcnm, fhca1, "
				+ "fhca2, fhcct, fhcst, fhczip, fhbl#, fhpud8 "
				+ "FROM FBFILES.FRP001 WHERE fhot = ? AND fhpro = ?";
		List<ProInfoResponseDTO> res = jdbcMyEstes.query(sql, new Object[] {ot, pro}, new ProMapper());

		if(res.size() > 0) {
			return res.get(0);
		}
		else {
			throw new ClaimsException(ClaimsConstant.ERROR_CODE);
		}
	}
	
	@Override
	public ClaimantResponseDTO getClaimantInfo(String username) throws ClaimsException {
		String sql = "select cmname,cmadr1,cmadr2,cmcity,cmst,cmzip,qsem1,qspnac,qspnfp,qspnlp,qsfnac,qsfnfp,qsfnlp " + 
				"from estesrtgy2.qnp230 " + 
				"join fbfiles.rap001 on qsact# = cmcust " + 
				"where qsun = ?";
		List<ClaimantResponseDTO> res = jdbcMyEstes.query(sql, new Object[] {username}, new ClaimantMapper());

		if(res.size() > 0) {
			return res.get(0);
		}
		else {
			throw new ClaimsException(ClaimsConstant.ERROR_CODE);
		}
	}
	
	@Override
	public boolean isPartyToShipment(String account, String accountType, String ot, String pro) throws ClaimsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName(SP_ISHIPARTY);
		sproc.addDeclaredParameter(new SqlParameter("ACCOUNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("OT", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlParameter("PRO", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("ACCOUNT", account);
		inParams.put("TYPE", accountType);
		inParams.put("OT", ot);
		inParams.put("PRO", pro);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String error = (String) outParms.get("ERROR");
	    		if("".equals(error)) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ");
				throw new ClaimsException("Failed to see if account is party to shipment");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ", e);
			throw new ClaimsException("Failed to see if account is party to shipment");
		}
	}
	
	@Override
	public boolean isL2LShipment(String OT) throws ClaimsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName(SP_ISL2L);
		sproc.addDeclaredParameter(new SqlParameter("PR_OTPRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("PR_VALID", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_OTPRO", OT);
		inParams.put("PR_VALID", "");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String response = (String) outParms.get("PR_VALID");
	    		if("Y".equals(response)) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
				throw new ClaimsException("Failed checking if l2l shipment ");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
			throw new ClaimsException("Failed checking if l2l shipment ");
		}
	}
	
	@Override
	public String accountTypeForCode(String accountCode) throws ClaimsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName(SP_ACCOUNTTYPE);
		sproc.addDeclaredParameter(new SqlParameter("SP_ACCOUNT_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("SP_ACCOUNT_TYPE", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("SP_ACCOUNT_NUMBER", accountCode);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String response = (String) outParms.get("SP_ACCOUNT_TYPE");
	    		return response;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "accountTypeForCode()", "Failed getting account type");
				throw new ClaimsException("Failed getting account type");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "accountTypeForCode()", "Failed getting account type", e);
			throw new ClaimsException("Failed getting account type");
		}
	}
	
	@Override
	public boolean hasEnteredClaim(String ot, String pro) throws ClaimsException {
		String sql = "select count(ahclm) from fbfiles.clp001 where ahot = ? and ahpro = ?";
		Integer res = jdbcMyEstes.queryForObject(sql, new Object[] {ot, pro}, Integer.class);

		if(res != null && res > 0){
			return true;
		}
		
		sql = "select count(claim_num) from fbfiles.staging_claim_header where orig_term = ? and pro_num = ?";
		res = jdbcMyEstes.queryForObject(sql, new Object[] {ot, pro}, Integer.class);

		if(res != null && res > 0){
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getClaimTypeNumber(String claimType, String otPro) throws ClaimsException {
		if(claimType.equals("Damage")) {
			String otAndPro[] = otPro.split("-");
			if(otAndPro.length != 2) {
				throw new ClaimsException("Invalid OT PRO");
			}
			
			String sql = "select FHBBC "
					+ "FROM FBFILES.FRP001 WHERE fhot = ? AND fhpro = ?";
			String res = jdbcMyEstes.queryForObject(sql, new Object[] {otAndPro[0], otAndPro[1]}, String.class);

			if(res != null && (res.trim().equals("DD") || res.trim().equals("RTS") || res.trim().equals("PR"))){
				return "5";
			}
			return "4";
		}
		else if(claimType.equals("Loss")){
			return "1";
		}
		else {
			return "0";
		}
	}
	
	// this call shouldn't be to the qnp150 table but old application gets it from cookie
	// and company name isn't moved to the qnp230 table yet.
	// After it moved change to select from 230
	@Override
	public String getCompanyName(String username) throws ClaimsException {
		String sql = "select QRCNAM from estesrtgy2.qnp150  WHERE QRUN=?";
		String res = "";
		try{
		 res = jdbcMyEstes.queryForObject(sql, new Object[] {username}, String.class);
		}catch(Exception ex){
			ESTESLogger.log(this.getClass(), username+" does not have any record on qnp150 table.");
		}
		return res;
	}
	
	@Override
	public String writeHeader(String username, SubmitClaimRequestDTO claim, String claimTypeNumber)
			throws ClaimsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName(SP_CLAIM_HEADER);
		sproc.addDeclaredParameter(new SqlOutParameter("SP_CLAIM_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_COMP_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_TYPE_CLAIM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_TYPE_FREIGHT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_ORIG_TERM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_PRO_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_FB_DATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_ADR1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_ADR2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_ST", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_CUST_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_CLAIMANT_REF", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_ADR1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_ADR2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_ST", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_CUST_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REMIT_TO_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_REQ_AMT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_COMMENTS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_BOL#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_BOL_DATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SP_LAST_USER", Types.CHAR));
		
		String otAndPro[] = claim.getOtPro().split("-");
		if(otAndPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		Date proDate = formatDate(claim.getProDate());
		Date bolDate = formatDate(claim.getBolDate());
		Map<String, Object> inParams = new HashMap<String, Object>();
		/*1	inParams.put("SP_CLAIM_NUM", 		"");*/
		/*2*/	inParams.put("SP_COMP_CODE", 			claim.getAccountNumber());
		/*3*/	inParams.put("SP_TYPE_CLAIM", 			claimTypeNumber);
		/*4*/	inParams.put("SP_TYPE_FREIGHT", 		claim.getFreightType());
		/*5*/	inParams.put("SP_ORIG_TERM", 			formatIntString(otAndPro[0]));
		/*6*/	inParams.put("SP_PRO_NUM", 				formatIntString(otAndPro[1]));
		/*7*/	inParams.put("SP_FB_DATE", 				new java.sql.Date(proDate.getTime()));
		/*8*/	inParams.put("SP_CLAIMANT_CUST_CODE",	claim.getAccountNumber());
		/*9*/	inParams.put("SP_CLAIMANT_CUST_NAME",	nullSafeAllCaps(claim.getName()));
		/*10*/	inParams.put("SP_CLAIMANT_CUST_ADR1",	nullSafeAllCaps(claim.getStreetAddress1()));
		/*11*/	inParams.put("SP_CLAIMANT_CUST_ADR2",	nullSafeAllCaps(claim.getStreetAddress2()));
		/*12*/	inParams.put("SP_CLAIMANT_CUST_CITY",	nullSafeAllCaps(claim.getCity()));
		/*13*/	inParams.put("SP_CLAIMANT_CUST_ST", 	nullSafeAllCaps(claim.getState()));
		/*14*/	inParams.put("SP_CLAIMANT_CUST_ZIP", 	nullSafeAllCaps(claim.getZip()));
		/*15*/	inParams.put("SP_CLAIMANT_EMAIL", 		claim.getEmail());
		/*16*/	inParams.put("SP_CLAIMANT_PHONE", 		formatIntString(PhoneUtil.convertToDigits(claim.getPhone())));
		/*17*/	inParams.put("SP_CLAIMANT_FAX", 		formatIntString(PhoneUtil.convertToDigits(claim.getFax())));
		/*18*/	inParams.put("SP_CLAIMANT_REF", 		nullSafeAllCaps(claim.getReferenceNumber()));
		/*19*/	inParams.put("SP_REMIT_TO_CUST_CODE", 	claim.getAccountNumber());
		/*20*/	inParams.put("SP_REMIT_TO_CUST_NAME", 	nullSafeAllCaps(claim.getRemitName()));
		/*21*/	inParams.put("SP_REMIT_TO_CUST_ADR1", 	nullSafeAllCaps(claim.getRemitStreetAddress1()));
		/*22*/	inParams.put("SP_REMIT_TO_CUST_ADR2", 	nullSafeAllCaps(claim.getRemitStreetAddress2()));
		/*23*/	inParams.put("SP_REMIT_TO_CUST_CITY",	nullSafeAllCaps(claim.getRemitCity()));
		/*24*/	inParams.put("SP_REMIT_TO_CUST_ST", 	nullSafeAllCaps(claim.getRemitState()));
		/*25*/	inParams.put("SP_REMIT_TO_CUST_ZIP", 	nullSafeAllCaps(claim.getRemitZip()));
		/*26*/	inParams.put("SP_REMIT_TO_PHONE",		formatIntString(PhoneUtil.convertToDigits(claim.getRemitPhone())));
		/*27*/	inParams.put("SP_REQ_AMT", 				formatDoubleString(claim.getAutoTotal()));
		/*28*/  inParams.put("SP_COMMENTS",    			claim.getAdditionalComments().replaceAll("\r", ""));
		/*29*/	inParams.put("SP_BOL#", 				claim.getBol());
		/*30*/	inParams.put("SP_BOL_DATE", 			new java.sql.Date(bolDate.getTime()));
		/*31*/	inParams.put("SP_LAST_USER", 			username);

		try {
			ESTESLogger.log(ESTESLogger.DEBUG, FilingDAOImpl.class, "writeHeader", "Before Call");
			Map<String, Object> outParms = sproc.execute(inParams);
			ESTESLogger.log(ESTESLogger.DEBUG, FilingDAOImpl.class, "writeHeader", "After Call");

	        if (outParms != null) {
	    		String response = (String) outParms.get("SP_CLAIM_NUM");
	    		return response;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "writeHeader()", "An error occurred writing the header");
				throw new ClaimsException("Failed to write header");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "writeHeader()", "An error occurred writing the header", e);
			throw new ClaimsException("Failed to write header");
		}
	}
	
	@Override
	public void writeDetails(String claimsNumber, String claimTypeNumber, ClaimsFormFileItems fileItems) throws ClaimsException {
		this.writeDetailRecord(claimsNumber, claimTypeNumber,"INVOICE", 		"0", "0", "", fileItems.getInvoice().getPathOnly(), 	fileItems.getInvoice().getFileName());
		this.writeDetailRecord(claimsNumber, claimTypeNumber,"BOL", 			"0", "0", "", fileItems.getBOL().getPathOnly(), 		fileItems.getBOL().getFileName());
		this.writeDetailRecord(claimsNumber, claimTypeNumber,"FREIGHT BILL", 	"0", "0", "", fileItems.getFreightBill().getPathOnly(), fileItems.getFreightBill().getFileName());
		this.writeDetailRecord(claimsNumber, claimTypeNumber,"OTHER", 		"0", "0", "", fileItems.getOther().getPathOnly(), 		fileItems.getOther().getFileName());
		for(int x = 0; x<10; x++){
			this.writeDetailRecord(claimsNumber, claimTypeNumber, String.valueOf(x), fileItems.getDetailsQuantity(x), fileItems.getDetailsCost(x), fileItems.getDetailsDescription(x), fileItems.getDetail(x).getPathOnly(), fileItems.getDetail(x).getFileName());
		}
	}
	
	
	private void writeDetailRecord(String claimNumber, String claimTypeNumber, String key, String quantity, String cost, String description, String path, String file) throws ClaimsException {
		String sql = "INSERT INTO " + CLAIM_DETAIL_TABLE + 
				" (claim_num, doc_key, no_pieces, " + 
				" type_claim, type_frght, req_amt, detail_line_cmnt, path, file)" +
				" VALUES (?,?,?,?,?,?,?,?,?)";
		
		Object[] values = {
				this.formatInt(claimNumber),
				key,
				this.formatInt(quantity),
				claimTypeNumber,
				561,
				String.valueOf(this.formatDouble(cost)),
				description,
				path,
				file
		};
		try {
			jdbcMyEstes.update(sql,  values);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "writeDetailRecord()", "** Error writing detailed record.", e);
    		throw new ClaimsException("Error writing detailed record.");
		}
	}
	private class ProMapper implements RowMapper<ProInfoResponseDTO> {

		@Override
		public ProInfoResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProInfoResponseDTO proInfo = new ProInfoResponseDTO();
			
			
			proInfo.setShipperName(rs.getString("fhsnm"));
			Address shipperAddress = new Address();
			shipperAddress.setStreetAddress(rs.getString("fhsa1"));
			shipperAddress.setStreetAddress2(rs.getString("fhsa2"));
			shipperAddress.setCity(rs.getString("fhsct"));
			shipperAddress.setState(rs.getString("fhsst"));
			shipperAddress.setZip(rs.getString("fhszip"));
			proInfo.setShipperAddress(shipperAddress);
			proInfo.setConsigneeName(rs.getString("fhcnm"));
			Address consigneeAddress = new Address();
			consigneeAddress.setStreetAddress(rs.getString("fhca1"));
			consigneeAddress.setStreetAddress2(rs.getString("fhca2"));
			consigneeAddress.setCity(rs.getString("fhcct"));
			consigneeAddress.setState(rs.getString("fhcst"));
			consigneeAddress.setZip(rs.getString("fhczip"));
			proInfo.setConsigneeAddress(consigneeAddress);
			proInfo.setBolNum(rs.getString("fhbl#"));
			proInfo.setBolDate(rs.getString("fhpud8"));
			proInfo.setProDate(rs.getString("fhpud8"));
			
			return proInfo;
		}
		
	}
	
	private class ClaimantMapper implements RowMapper<ClaimantResponseDTO> {

		@Override
		public ClaimantResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClaimantResponseDTO info = new ClaimantResponseDTO();
			
			info.setName(rs.getString("cmname"));
			Address address = new Address();
			address.setStreetAddress(rs.getString("cmadr1"));
			address.setStreetAddress2(rs.getString("cmadr2"));
			address.setCity(rs.getString("cmcity"));
			address.setState(rs.getString("cmst"));
			address.setZip(rs.getString("cmzip"));
			info.setAddress(address);
			info.setEmail(rs.getString("qsem1"));
			info.setPhone(rs.getString("qspnac")+rs.getString("qspnfp")+rs.getString("qspnlp"));
			info.setFax(rs.getString("qsfnac")+rs.getString("qsfnfp")+rs.getString("qsfnlp"));
			
			return info;
		}
		
	}
	
	/**
	 * Null-safe wrapper method for String.toUpperCase().
	 * 
	 * @param value the String value
	 * @return the value uppercased or null
	 */
	public static String nullSafeAllCaps(String value) {
		return value == null? value: value.toUpperCase();
	}
	
	/**
	 * Formatting from old my estes
	 * 
	 * @param value the String value
	 * @return the value in long format or 0
	 */
	private String formatIntString(String num){
		if(StringFormat.containsData(num) && StringFormat.isNumeric(num)){
			return String.valueOf(Long.parseLong(num));
		}else{
			return "0";
		}
	}
	
	/**
	 * Formatting from old my estes
	 * 
	 * @param value the String value
	 * @return the value in double format or 0.0
	 */
	private String formatDoubleString(String num){
		if(StringFormat.containsData(num) && StringFormat.isNumeric(num)){
			return String.valueOf(Double.parseDouble(num));
		}else{
			return "0.0";
		}
	}
	
	/**
	 * Format to insert into database
	 * 
	 * @param inDate
	 * @return
	 */
	private Date formatDate(String inDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);
		
		try {
		    return new Date(dateFormat.parse(inDate.trim()).getTime());
		} catch(ParseException e) {
			try {
				return new Date(dateFormat.parse("19000101").getTime());
			} catch(ParseException e2){
				ESTESLogger.log(ESTESLogger.ERROR, FilingDAOImpl.class, "formatDate()", e2.getMessage(), e2);
			}
		}
		
		return null;
	}
	
	/**
	 * Formats int to be 0 or int value of string
	 * 
	 * @param num
	 * @return int value of string
	 */
	private int formatInt(String num){
		if(StringFormat.containsData(num) && StringFormat.isNumeric(num)){
			return Integer.parseInt(num);
		}else{
			return 0;
		}
	}
	
	/**
	 * Formats double to be 0.0 or double value of string
	 * 
	 * @param num
	 * @return double value of string
	 */
	private double formatDouble(String num){
		if(StringFormat.containsData(num) && StringFormat.isNumeric(num)){
			return Double.parseDouble(num);
		}else{
			return 0.0;
		}
	}
}