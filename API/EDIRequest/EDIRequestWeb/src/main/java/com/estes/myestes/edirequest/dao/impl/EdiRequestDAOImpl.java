/**
 * @author: Lakshman K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.edirequest.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.edirequest.dao.iface.EdiRequestDAO;
import com.estes.myestes.edirequest.dto.BillingHeaderType;
import com.estes.myestes.edirequest.dto.Contact;
import com.estes.myestes.edirequest.dto.Customer;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.exception.EdiRequestException;
import com.estes.myestes.edirequest.utils.EdiRequestConstant;
import com.estes.myestes.edirequest.utils.EdiRequestUtil;

@Repository ("ediRequestDAO")
public class EdiRequestDAOImpl implements EdiRequestDAO, EdiRequestConstant
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class BillingTypeMapper implements RowMapper<BillingHeaderType>
	{
		@Override
		public BillingHeaderType mapRow(ResultSet rs, int rowNm) throws SQLException {
			BillingHeaderType elem = new BillingHeaderType();

			elem.setId(rs.getString("BTPID").trim());
			elem.setDescription(rs.getString("BTPTYP").trim());

			return elem;
		}	
	} // BillingTypeMapper
	
	private static final class HeaderTypeMapper implements RowMapper<BillingHeaderType>
	{
		@Override
		public BillingHeaderType mapRow(ResultSet rs, int rowNm) throws SQLException {
			BillingHeaderType elem = new BillingHeaderType();

			elem.setId(rs.getString("HTPID").trim());
			elem.setDescription(rs.getString("HTPTYP").trim());

			return elem;
		}	
	} // HeaderTypeMapper
	
	@Override
	public List<BillingHeaderType> getEdiBillingTypes() throws EdiRequestException
	{
		List<BillingHeaderType> billingTypeList = null;
		String sql = "SELECT BTPID, BTPTYP FROM FBFILES.DERBTYPP";
		try {
			List<BillingHeaderType> listQuery =  jdbcMyEstes.query(sql,  new Object[] {}, new BillingTypeMapper());
			if (listQuery != null && listQuery.size() > 0) {
				billingTypeList =  listQuery;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAO.class, "getEdiBillingTypes()", "** Error retrieving the billing types.", e);
			throw new EdiRequestException("Billing types could not be retrieved.");
		}
		return billingTypeList;
	} // getCommodities
	
	@Override
	public List<BillingHeaderType> getEdiHeaderTypes() throws EdiRequestException {
		List<BillingHeaderType> headerTypeList = null;
		String sql = "SELECT HTPID, HTPTYP FROM FBFILES.DERHTYPP";
		try {
			List<BillingHeaderType> listQuery =  jdbcMyEstes.query(sql,  new Object[] {}, new HeaderTypeMapper());
			if (listQuery != null && listQuery.size() > 0) {
				headerTypeList =  listQuery;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "getEdiHeaderTypes()", "** Error retrieving the header types.", e);
			throw new EdiRequestException("Header types could not be retrieved.");
		}
		return headerTypeList;
	}
	
	public String saveCustomerInformation(EdiRequest ediRequest) throws EdiRequestException
	{
		boolean success = false;
		String sql = "INSERT INTO FBFILES.DERCUSP (CUSREQ, CUSNAM, CUSADR, CUSCTY, CUSST, CUSZIP, CUSPHN, CUSFAX, CUSWEB, CUSPAY, CUSMAN, CUSDAT) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Customer customer = ediRequest.getCustomer();
		String ediRequestId = getNextEdiRequestNumber();
		String createDate = getCreateDate();
		String phoneNum = getPhoneFaxNum(customer.getPhone(), customer.getExtension());
		String fax = getPhoneFaxNum(customer.getFax(), null);
		
		Object[] values = {ediRequestId , 
							customer.getName(), 
							customer.getAddress(), 
							customer.getCity(), 
							customer.getState(), 
							customer.getZip(), 
							phoneNum, 
							fax, 
							customer.getWebsite(),
							customer.getFreightPaymentAgency(),
							customer.getFreightManagementCompany(), 
							createDate
						 };
		try {
			success = jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			if(success) {
				return ediRequestId;
			} else {
				return null;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveCustomerInformation()", "** Error inserting customer. ", e);
    		throw new EdiRequestException("Error adding customer .");
		}
	} // saveCustomerInformation
	
	public boolean saveContactInformation(Contact contact, String ediRequestId, String contactType) throws EdiRequestException
	{
		String sql = "INSERT INTO FBFILES.DERCONP (CONREQ, CONTYP, CONNAM, CONTTL, CONEML, CONPHN, CONFAX ) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		String phoneNum = getPhoneFaxNum(contact.getPhone(), contact.getExtension());
		String fax = getPhoneFaxNum(contact.getFax(), null);
		
		Object[] values = {ediRequestId , contactType, contact.getName(), contact.getTitle(), 
							contact.getEmail(), phoneNum, fax};
		try {
			return jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveContactInformation()", "** Error inserting contact info. ", e);
    		throw new EdiRequestException("Error adding contact .");
		}
	} // saveContactInformation
	
	public boolean saveEdiRequestInformation(EdiRequest ediRequest, String ediRequestId) throws EdiRequestException
	{
		String sql = "INSERT INTO FBFILES.DERREQP (REQREQ, REQBTYP, REQHTYP, REQTPNK, REQTPNO, REQTPNV, REQBLS, REQNAM, REQPHN, REQEML, " + 
				"REQ204, REQ210, REQ211, REQ212, REQ214, REQ990, REQOTH, REQACPT, REQREP, REQPIC, REQBOL, REQCMT ) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String phoneNum = getPhoneFaxNum(ediRequest.getFillersPhone(), ediRequest.getFillerExtn());
		
		Object[] values = {ediRequestId , ediRequest.getEdiBillingType(),
							ediRequest.getEdiHeaderType(),
							ediRequest.getThirdPartyNetworks(),
							ediRequest.getOtherThirdPartyCheck(),
							ediRequest.getOtherThirdPartyValue(),
							ediRequest.getWillReservedBillsUsed(),
							ediRequest.getFillersName(),
							phoneNum,
							ediRequest.getFillersEmail(),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm204()),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm210()),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm211()),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm212()),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm214()),
							EdiRequestUtil.getStringFromBool(ediRequest.isForm990()),
							EdiRequestUtil.getStringFromBool(ediRequest.isFormOther()),
							ediRequest.getAutoAccept(),
							ediRequest.getHas214ReportCard(),
							ediRequest.getUse211AsPickupRequest(),
							ediRequest.getSendReserveProsInBOL06(),
							ediRequest.getFormComments()
						 };
		try {
			return jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveEdiRequestInformation()", "** Error inserting edi request. ", e);
    		throw new EdiRequestException("Error adding edi request .");
		}
	} // saveEdiRequestInformation
	
	public boolean saveEdiRequestAccountInformation(String ediRequestId, String ediAccountNumber) throws EdiRequestException
	{
		String sql = "INSERT INTO FBFILES.DERACTP (ACTREQ, ACTACT) VALUES (?, ?)";
		
		Object[] values = {ediRequestId , ediAccountNumber};
		try {
			return jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveEdiRequestAccountInformation()", "** Error inserting edi request account. ", e);
    		throw new EdiRequestException("Error adding edi request account .");
		}
	} // saveEdiRequestAccountInformation
	
	public boolean saveEdiRequestAddressInformation(String ediRequestId, String ediAddressLoc) throws EdiRequestException
	{
		String sql = "INSERT INTO FBFILES.DERADRP (ADRREQ, ADRADR) VALUES (?, ?)";
		
		Object[] values = {ediRequestId , ediAddressLoc};
		try {
			return jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveEdiRequestAddressInformation()", "** Error inserting edi request address. ", e);
    		throw new EdiRequestException("Error adding edi request address .");
		}
	} // saveEdiRequestAddressInformation
	
	public boolean saveEdiRequestCommunicationInformation(String ediRequestId, EdiRequest ediRequest) throws EdiRequestException
	{
		String sql = "INSERT INTO FBFILES.DERCOMP (COMREQ, COMADR, COMDIR, COMUSR, COMPAS, COMPPAS, COMPRCV, COMPISQ," + 
				" COMPISR, COMPGS, COMTPAS, COMTRCV, COMTISQ, COMTISR, COMTGS, COMTDCC) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Object[] values = {ediRequestId , ediRequest.getFtpServerAddress(),
							ediRequest.getFtpDirectoryPath(),
							ediRequest.getFtpUsername(),
							ediRequest.getFtpPassword(),
							ediRequest.getProdBg01Password(),
							ediRequest.getProdBg03ReceiverId(),
							ediRequest.getProdISAQualifier(),
							ediRequest.getProdISAReceiverId(),
							ediRequest.getProdGSId(),
							ediRequest.getTestBg01Password(),
							ediRequest.getTestBg03ReceiverId(),
							ediRequest.getTestISAQualifier(),
							ediRequest.getTestISAReceiverId(),
							ediRequest.getTestGSId(),
							ediRequest.getTdccAnsiVersion()
						  };
		try {
			return jdbcMyEstes.update(sql,  values) > 0 ? true : false;
			
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "saveEdiRequestCommunicationInformation()", "** Error inserting edi request communication. ", e);
    		throw new EdiRequestException("Error adding edi request communication .");
		}
	} // saveEdiRequestCommunicationInformation
	
	@Override
	public String saveEdiFormTransmissionRequest(EdiRequest ediRequest) throws EdiRequestException {
		String ediRequestNumber = saveCustomerInformation(ediRequest);
		if(!isContactEmpty(ediRequest.getPrimaryEdiContact())) {
			saveContactInformation(ediRequest.getPrimaryEdiContact(),ediRequestNumber,PRIMARY_EDI_CONTACT_TYPE);
		}
		if(!isContactEmpty(ediRequest.getSecondaryEdiContact())) {
			saveContactInformation(ediRequest.getSecondaryEdiContact(),ediRequestNumber,SECONDARY_EDI_CONTACT_TYPE);
		}
		if(!isContactEmpty(ediRequest.getAccountsPayableContact())) {
			saveContactInformation(ediRequest.getAccountsPayableContact(),ediRequestNumber,ACCOUNTS_PAYABLE_CONTACT_TYPE);
		}
		if(!isContactEmpty(ediRequest.getBusinessContact())) {
			saveContactInformation(ediRequest.getBusinessContact(),ediRequestNumber,BUSINESS_CONTACT_TYPE);
		}
		if(!isContactEmpty(ediRequest.getTrafficContact())) {
			saveContactInformation(ediRequest.getTrafficContact(),ediRequestNumber,TRAFFIC_CONTACT_TYPE);
		}
		if(!isContactEmpty(ediRequest.getAdditionalContact())) {
			saveContactInformation(ediRequest.getAdditionalContact(),ediRequestNumber,ADDITIONAL_CONTACT_TYPE);
		}
		
		if(!isEdiRequestInformationEmpty(ediRequest)) {
			saveEdiRequestInformation(ediRequest, ediRequestNumber);
		}
		
		for(String accNumber : ediRequest.getEstesAccountNumber()){
			String ediAccountNumber = accNumber.trim();
			if(ediAccountNumber!=null && !ediAccountNumber.equals(""))
				saveEdiRequestAccountInformation(ediRequestNumber, ediAccountNumber);
		}
		
		for(String addressLoc : ediRequest.getEdiAddressLocation()){
			String ediAddressLocation = addressLoc.trim();
			if(ediAddressLocation!=null && !ediAddressLocation.equals(""))
				saveEdiRequestAddressInformation(ediRequestNumber, ediAddressLocation);	
		}
		
		String ediCommunicationDetails = ediRequest.getFtpServerAddress().trim()+ediRequest.getFtpDirectoryPath().trim()+ 
										ediRequest.getFtpUsername().trim()+ ediRequest.getFtpPassword().trim()+ ediRequest.getProdBg01Password().trim()+ 
										ediRequest.getProdBg03ReceiverId().trim()+ediRequest.getProdISAQualifier().trim()+ediRequest.getProdISAReceiverId().trim()+ 
										ediRequest.getProdGSId().trim()+ediRequest.getTestBg01Password().trim()+ ediRequest.getTestBg03ReceiverId().trim()+
										ediRequest.getTestISAQualifier().trim()+ ediRequest.getTestISAReceiverId().trim()+ediRequest.getTestGSId().trim()+
										ediRequest.getTdccAnsiVersion().trim();
		if(ediCommunicationDetails.length() != 0){
			saveEdiRequestCommunicationInformation(ediRequestNumber, ediRequest);
		}
	
		return ediRequestNumber;
	}
	
	private String getCreateDate() {
		Calendar todaysDate = Calendar.getInstance();
		int todays_YY = todaysDate.get(Calendar.YEAR);
		int todays_MM = todaysDate.get(Calendar.MONTH)+1;
		int todays_DD = todaysDate.get(Calendar.DAY_OF_MONTH);
		int todays_hh = todaysDate.get(Calendar.HOUR_OF_DAY);
		int todays_mm = todaysDate.get(Calendar.MINUTE);
		int todays_ss = todaysDate.get(Calendar.SECOND);
		String createDate = todays_YY+"-"+
							(todays_MM<10?"0"+todays_MM:todays_MM+"")+"-"+
							(todays_DD<10?"0"+todays_DD:todays_DD+"")+"-"+
							(todays_hh<10?"0"+todays_hh:todays_hh+"")+"."+
							(todays_mm<10?"0"+todays_mm:todays_mm+"")+"."+
							(todays_ss<10?"0"+todays_ss:todays_ss+"")+".000000";
		return createDate;
	}
	
	private String getPhoneFaxNum(String phone, String extn) {
		if(extn == null) extn = "";
		String phoneNum = EdiRequestUtil.getAreaCode(phone)+
				EdiRequestUtil.getExchange(phone)+
				EdiRequestUtil.getLast4(phone)+
				extn;
		if (phoneNum.length() == 0) phoneNum = "0";
		return phoneNum;
	}
	
	private String getNextEdiRequestNumber() throws EdiRequestException {
		String newEdiRequestNumber = null;
		String sql = "SELECT MAX(CUSREQ) FROM FBFILES.DERCUSP";
		try {
			String ediRequestId =  jdbcMyEstes.queryForObject(sql, String.class, new Object[] {});
			newEdiRequestNumber = (Double.valueOf(ediRequestId)+1.0)+"";
			if(newEdiRequestNumber.indexOf(".")!=-1)
				newEdiRequestNumber = newEdiRequestNumber.substring(0,newEdiRequestNumber.indexOf("."));
			return newEdiRequestNumber;
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestDAOImpl.class, "getNextEdiRequestNumber()", "** Error retrieving edi request id. ", e);
    		throw new EdiRequestException("Error retrieving edi request id.");
		}	
	}
		
	private boolean isEdiRequestInformationEmpty(EdiRequest ediRequest){
		String ediRequestDetails = ediRequest.getEdiBillingType().trim()+ediRequest.getEdiHeaderType().trim()+
				ediRequest.getOtherThirdPartyValue().trim()+
				ediRequest.getFillersName().trim()+ediRequest.getFillersEmail().trim()+
								   EdiRequestUtil.getAreaCode(ediRequest.getFillersPhone())+
								   EdiRequestUtil.getExchange(ediRequest.getFillersPhone())+
								   EdiRequestUtil.getLast4(ediRequest.getFillersPhone())+
								  (ediRequest.getFillerExtn() != null ? ediRequest.getFillerExtn() : "");
		
		if(ediRequestDetails.length()==0 && EdiRequestUtil.getStringFromBool(ediRequest.isForm204()).equalsIgnoreCase(NO) &&
				EdiRequestUtil.getStringFromBool(ediRequest.isForm210()).equalsIgnoreCase(NO) && EdiRequestUtil.getStringFromBool(ediRequest.isForm211()).equalsIgnoreCase(NO) &&
				EdiRequestUtil.getStringFromBool(ediRequest.isForm212()).equalsIgnoreCase(NO) && EdiRequestUtil.getStringFromBool(ediRequest.isForm214()).equalsIgnoreCase(NO) && 
				EdiRequestUtil.getStringFromBool(ediRequest.isForm990()).equalsIgnoreCase(NO) && EdiRequestUtil.getStringFromBool(ediRequest.isFormOther()).equalsIgnoreCase(NO) &&
				ediRequest.getAutoAccept().equalsIgnoreCase(NO) && ediRequest.getHas214ReportCard().equalsIgnoreCase(NO) &&
				ediRequest.getUse211AsPickupRequest().equalsIgnoreCase(NO) && ediRequest.getSendReserveProsInBOL06().equalsIgnoreCase(NO) &&
				ediRequest.getWillReservedBillsUsed().equalsIgnoreCase(NO) && ediRequest.getThirdPartyNetworks().equalsIgnoreCase(NO)&&
				ediRequest.getOtherThirdPartyCheck().equalsIgnoreCase(NO)){		
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isContactEmpty(Contact contact){
		return (contact.getName() != null ? contact.getName().trim().length() : 0) +
		(contact.getEmail() != null ? contact.getEmail().trim().length() : 0)+
		contact.getTitle().trim().length()+
		EdiRequestUtil.getAreaCode(contact.getFax()).trim().length()+
		EdiRequestUtil.getExchange(contact.getFax()).trim().length()+
		EdiRequestUtil.getLast4(contact.getFax()).trim().length()+
		EdiRequestUtil.getAreaCode(contact.getPhone()).trim().length()+
		EdiRequestUtil.getExchange(contact.getPhone()).trim().length()+
		EdiRequestUtil.getLast4(contact.getPhone()).trim().length()+
		(contact.getExtension() != null ? contact.getExtension().trim().length() : 0) == 0;
	}
}
