/**
 *  * @author: Lakshman K
 *
 * Creation date: 1/3/2019
 */
package com.estes.myestes.edirequest.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes Commodity
 */
public class EdiRequest
{
	//Customer Info
	@ApiModelProperty(notes=" Customer Information ")
	private Customer customer;
	//contacts
	@ApiModelProperty(notes=" Primary Edi contact ")
	private Contact primaryEdiContact;
	@ApiModelProperty(notes=" Secondary Edi contact ")
	private Contact secondaryEdiContact;
	@ApiModelProperty(notes=" Accounts payable contact ")
	private Contact accountsPayableContact;
	@ApiModelProperty(notes=" Business contact ")
	private Contact businessContact;
	@ApiModelProperty(notes=" traffic contact ")
	private Contact trafficContact;
	@ApiModelProperty(notes=" Additional contact ")
	private Contact additionalContact;	
	//edi type
	@ApiModelProperty(notes=" Edi billing type ")
	private String ediBillingType="";	
	//form document types
	@ApiModelProperty(notes=" Load tender / Pickup request ")
	private boolean form204;
	@ApiModelProperty(notes=" Invoice ")
	private boolean form210;
	@ApiModelProperty(notes=" Bill of lading ")
	private boolean form211;
	@ApiModelProperty(notes=" Trailer manifest ")
	private boolean form212;
	@ApiModelProperty(notes=" Shipment status ")
	private boolean form214;
	@ApiModelProperty(notes=" Response to a load tender ")
	private boolean form990;
	@ApiModelProperty(notes=" Other document type ")
	private boolean formOther;	
	@ApiModelProperty(notes=" Auto accept - Y/N ")
	private String autoAccept="";
	@ApiModelProperty(notes=" Can you provide a 214 report card to Estes Express? - Y/N ")
	private String has214ReportCard="";
	@ApiModelProperty(notes=" Use 211 as pickup request? -  Y/N")
	private String use211AsPickupRequest="";
	@ApiModelProperty(notes=" Are you able to send reserve PRO numbers in the BOL06? - Y/N ")
	private String sendReserveProsInBOL06="";
	@ApiModelProperty(notes=" Comments ")
	private String formComments="";	
	//estes acc num
	@ApiModelProperty(notes=" Estes account number ")
	private List<String> estesAccountNumber;
	//address for edi location
	@ApiModelProperty(notes=" Edi location ")
	private List<String> ediAddressLocation;
	//reserved bills	
	@ApiModelProperty(notes=" Will reserved bills be used? - Y/N ")
	private String willReservedBillsUsed="";
	//third party
	@ApiModelProperty(notes="  Third Party Networks for EDI Applications: Estes Express utilizes Kleinschmidt ")
	private String thirdPartyNetworks="";
	@ApiModelProperty(notes=" Other third party ?")
	private String otherThirdPartyCheck="";
	@ApiModelProperty(notes=" Other third party name ")
	private String otherThirdPartyValue="";	
	//edi header type
	@ApiModelProperty(notes=" Edi Header type ")
	private String ediHeaderType="";
	//ftp
	@ApiModelProperty(notes="Ftp Server address ")
	private String ftpServerAddress="";
	@ApiModelProperty(notes="Ftp Directory path ")
	private String ftpDirectoryPath="";
	@ApiModelProperty(notes="Ftp Username ")
	private String ftpUsername="";
	@ApiModelProperty(notes="Ftp Password ")
	private String ftpPassword="";
	//Header Info
	@ApiModelProperty(notes=" Production BG01(Password) ")
	private String prodBg01Password="";
	@ApiModelProperty(notes=" Production BG03(Receiver id) ")
	private String prodBg03ReceiverId="";
	@ApiModelProperty(notes=" Production ISA qualifier ")
	private String prodISAQualifier="";
	@ApiModelProperty(notes=" Production ISA receiver id ")
	private String prodISAReceiverId="";
	@ApiModelProperty(notes=" Production GS ID ")
	private String prodGSId="";
	@ApiModelProperty(notes=" Test BG01(Password) ")
	private String testBg01Password="";
	@ApiModelProperty(notes=" Test BG03(Receiver id) ")
	private String testBg03ReceiverId="";
	@ApiModelProperty(notes=" Test ISA Qualifier ")
	private String testISAQualifier="";
	@ApiModelProperty(notes=" Test ISA Receiver id ")
	private String testISAReceiverId="";
	@ApiModelProperty(notes=" Test GS id ")
	private String testGSId="";
	//tdcc
	@ApiModelProperty(notes=" TDCC/ANSI Version: 4010, 4030 etc... ")
	private String tdccAnsiVersion="";
	//person filling form
	@ApiModelProperty(notes=" Person completing form ")
	private String fillersName="";
	@ApiModelProperty(notes=" Filler's phone ")
	private String fillersPhone="";
	@ApiModelProperty(notes=" Phone Extn – xxxx ")
	private String fillerExtn="";
	@ApiModelProperty(notes=" Send a copy of this request to user's e-mail ")
	private String sendCopyInMail="";
	@ApiModelProperty(notes=" Filler's email ")
	private String fillersEmail="";
	@ApiModelProperty(notes=" Level 2 Logistics Request: Y/N ")
	private String isL2L="";
	private String referenceNumber;
	
	public EdiRequest() {
		super();
	}
	
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}


	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	/**
	 * @return the primaryEdiContact
	 */
	public Contact getPrimaryEdiContact() {
		return primaryEdiContact;
	}

	/**
	 * @param primaryEdiContact the primaryEdiContact to set
	 */
	public void setPrimaryEdiContact(Contact primaryEdiContact) {
		this.primaryEdiContact = primaryEdiContact;
	}

	/**
	 * @return the secondaryEdiContact
	 */
	public Contact getSecondaryEdiContact() {
		return secondaryEdiContact;
	}

	/**
	 * @param secondaryEdiContact the secondaryEdiContact to set
	 */
	public void setSecondaryEdiContact(Contact secondaryEdiContact) {
		this.secondaryEdiContact = secondaryEdiContact;
	}

	/**
	 * @return the accountsPayableContact
	 */
	public Contact getAccountsPayableContact() {
		return accountsPayableContact;
	}

	/**
	 * @param accountsPayableContact the accountsPayableContact to set
	 */
	public void setAccountsPayableContact(Contact accountsPayableContact) {
		this.accountsPayableContact = accountsPayableContact;
	}

	/**
	 * @return the businessContact
	 */
	public Contact getBusinessContact() {
		return businessContact;
	}

	/**
	 * @param businessContact the businessContact to set
	 */
	public void setBusinessContact(Contact businessContact) {
		this.businessContact = businessContact;
	}

	/**
	 * @return the trafficContact
	 */
	public Contact getTrafficContact() {
		return trafficContact;
	}

	/**
	 * @param trafficContact the trafficContact to set
	 */
	public void setTrafficContact(Contact trafficContact) {
		this.trafficContact = trafficContact;
	}

	/**
	 * @return the additionalContact
	 */
	public Contact getAdditionalContact() {
		return additionalContact;
	}

	/**
	 * @param additionalContact the additionalContact to set
	 */
	public void setAdditionalContact(Contact additionalContact) {
		this.additionalContact = additionalContact;
	}

	/**
	 * @return the ediBillingType
	 */
	public String getEdiBillingType() {
		return ediBillingType;
	}

	/**
	 * @param ediBillingType the ediBillingType to set
	 */
	public void setEdiBillingType(String ediBillingType) {
		this.ediBillingType = ediBillingType;
	}

	
	/**
	 * @return the form204
	 */
	public boolean isForm204() {
		return form204;
	}

	/**
	 * @param form204 the form204 to set
	 */
	public void setForm204(boolean form204) {
		this.form204 = form204;
	}

	/**
	 * @return the form210
	 */
	public boolean isForm210() {
		return form210;
	}

	/**
	 * @param form210 the form210 to set
	 */
	public void setForm210(boolean form210) {
		this.form210 = form210;
	}

	/**
	 * @return the form211
	 */
	public boolean isForm211() {
		return form211;
	}

	/**
	 * @param form211 the form211 to set
	 */
	public void setForm211(boolean form211) {
		this.form211 = form211;
	}

	/**
	 * @return the form212
	 */
	public boolean isForm212() {
		return form212;
	}

	/**
	 * @param form212 the form212 to set
	 */
	public void setForm212(boolean form212) {
		this.form212 = form212;
	}

	/**
	 * @return the form214
	 */
	public boolean isForm214() {
		return form214;
	}

	/**
	 * @param form214 the form214 to set
	 */
	public void setForm214(boolean form214) {
		this.form214 = form214;
	}

	/**
	 * @return the form990
	 */
	public boolean isForm990() {
		return form990;
	}

	/**
	 * @param form990 the form990 to set
	 */
	public void setForm990(boolean form990) {
		this.form990 = form990;
	}

	/**
	 * @return the formOther
	 */
	public boolean isFormOther() {
		return formOther;
	}

	/**
	 * @param formOther the formOther to set
	 */
	public void setFormOther(boolean formOther) {
		this.formOther = formOther;
	}

	/**
	 * @return the autoAccept
	 */
	public String getAutoAccept() {
		return autoAccept;
	}

	/**
	 * @param autoAccept the autoAccept to set
	 */
	public void setAutoAccept(String autoAccept) {
		this.autoAccept = autoAccept;
	}

	/**
	 * @return the has214ReportCard
	 */
	public String getHas214ReportCard() {
		return has214ReportCard;
	}

	/**
	 * @param has214ReportCard the has214ReportCard to set
	 */
	public void setHas214ReportCard(String has214ReportCard) {
		this.has214ReportCard = has214ReportCard;
	}

	/**
	 * @return the use211AsPickupRequest
	 */
	public String getUse211AsPickupRequest() {
		return use211AsPickupRequest;
	}

	/**
	 * @param use211AsPickupRequest the use211AsPickupRequest to set
	 */
	public void setUse211AsPickupRequest(String use211AsPickupRequest) {
		this.use211AsPickupRequest = use211AsPickupRequest;
	}

	/**
	 * @return the sendReserveProsInBOL06
	 */
	public String getSendReserveProsInBOL06() {
		return sendReserveProsInBOL06;
	}

	/**
	 * @param sendReserveProsInBOL06 the sendReserveProsInBOL06 to set
	 */
	public void setSendReserveProsInBOL06(String sendReserveProsInBOL06) {
		this.sendReserveProsInBOL06 = sendReserveProsInBOL06;
	}

	/**
	 * @return the formComments
	 */
	public String getFormComments() {
		return formComments;
	}

	/**
	 * @param formComments the formComments to set
	 */
	public void setFormComments(String formComments) {
		this.formComments = formComments;
	}

	
	/**
	 * @return the estesAccountNumber
	 */
	public List<String> getEstesAccountNumber() {
		return estesAccountNumber;
	}

	/**
	 * @param estesAccountNumber the estesAccountNumber to set
	 */
	public void setEstesAccountNumber(List<String> estesAccountNumber) {
		this.estesAccountNumber = estesAccountNumber;
	}

	/**
	 * @return the ediAddressLocation
	 */
	public List<String> getEdiAddressLocation() {
		return ediAddressLocation;
	}

	/**
	 * @param ediAddressLocation the ediAddressLocation to set
	 */
	public void setEdiAddressLocation(List<String> ediAddressLocation) {
		this.ediAddressLocation = ediAddressLocation;
	}

	/**
	 * @return the willReservedBillsUsed
	 */
	public String getWillReservedBillsUsed() {
		return willReservedBillsUsed;
	}

	/**
	 * @param willReservedBillsUsed the willReservedBillsUsed to set
	 */
	public void setWillReservedBillsUsed(String willReservedBillsUsed) {
		this.willReservedBillsUsed = willReservedBillsUsed;
	}

	/**
	 * @return the thirdPartyNetworks
	 */
	public String getThirdPartyNetworks() {
		return thirdPartyNetworks;
	}

	/**
	 * @param thirdPartyNetworks the thirdPartyNetworks to set
	 */
	public void setThirdPartyNetworks(String thirdPartyNetworks) {
		this.thirdPartyNetworks = thirdPartyNetworks;
	}

	/**
	 * @return the otherThirdPartyCheck
	 */
	public String getOtherThirdPartyCheck() {
		return otherThirdPartyCheck;
	}

	/**
	 * @param otherThirdPartyCheck the otherThirdPartyCheck to set
	 */
	public void setOtherThirdPartyCheck(String otherThirdPartyCheck) {
		this.otherThirdPartyCheck = otherThirdPartyCheck;
	}

	/**
	 * @return the otherThirdPartyValue
	 */
	public String getOtherThirdPartyValue() {
		return otherThirdPartyValue;
	}

	/**
	 * @param otherThirdPartyValue the otherThirdPartyValue to set
	 */
	public void setOtherThirdPartyValue(String otherThirdPartyValue) {
		this.otherThirdPartyValue = otherThirdPartyValue;
	}

	/**
	 * @return the ediHeaderType
	 */
	public String getEdiHeaderType() {
		return ediHeaderType;
	}

	/**
	 * @param ediHeaderType the ediHeaderType to set
	 */
	public void setEdiHeaderType(String ediHeaderType) {
		this.ediHeaderType = ediHeaderType;
	}

	/**
	 * @return the ftpServerAddress
	 */
	public String getFtpServerAddress() {
		return ftpServerAddress;
	}

	/**
	 * @param ftpServerAddress the ftpServerAddress to set
	 */
	public void setFtpServerAddress(String ftpServerAddress) {
		this.ftpServerAddress = ftpServerAddress;
	}

	/**
	 * @return the ftpDirectoryPath
	 */
	public String getFtpDirectoryPath() {
		return ftpDirectoryPath;
	}

	/**
	 * @param ftpDirectoryPath the ftpDirectoryPath to set
	 */
	public void setFtpDirectoryPath(String ftpDirectoryPath) {
		this.ftpDirectoryPath = ftpDirectoryPath;
	}

	/**
	 * @return the ftpUsername
	 */
	public String getFtpUsername() {
		return ftpUsername;
	}

	/**
	 * @param ftpUsername the ftpUsername to set
	 */
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	/**
	 * @return the ftpPassword
	 */
	public String getFtpPassword() {
		return ftpPassword;
	}

	/**
	 * @param ftpPassword the ftpPassword to set
	 */
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	/**
	 * @return the prodBg01Password
	 */
	public String getProdBg01Password() {
		return prodBg01Password;
	}

	/**
	 * @param prodBg01Password the prodBg01Password to set
	 */
	public void setProdBg01Password(String prodBg01Password) {
		this.prodBg01Password = prodBg01Password;
	}


	/**
	 * @return the prodISAQualifier
	 */
	public String getProdISAQualifier() {
		return prodISAQualifier;
	}

	/**
	 * @param prodISAQualifier the prodISAQualifier to set
	 */
	public void setProdISAQualifier(String prodISAQualifier) {
		this.prodISAQualifier = prodISAQualifier;
	}

	/**
	 * @return the prodBg03ReceiverId
	 */
	public String getProdBg03ReceiverId() {
		return prodBg03ReceiverId;
	}

	/**
	 * @param prodBg03ReceiverId the prodBg03ReceiverId to set
	 */
	public void setProdBg03ReceiverId(String prodBg03ReceiverId) {
		this.prodBg03ReceiverId = prodBg03ReceiverId;
	}

	/**
	 * @return the prodISAReceiverId
	 */
	public String getProdISAReceiverId() {
		return prodISAReceiverId;
	}

	/**
	 * @param prodISAReceiverId the prodISAReceiverId to set
	 */
	public void setProdISAReceiverId(String prodISAReceiverId) {
		this.prodISAReceiverId = prodISAReceiverId;
	}

	/**
	 * @return the prodGSId
	 */
	public String getProdGSId() {
		return prodGSId;
	}

	/**
	 * @param prodGSId the prodGSId to set
	 */
	public void setProdGSId(String prodGSId) {
		this.prodGSId = prodGSId;
	}

	/**
	 * @return the testBg01Password
	 */
	public String getTestBg01Password() {
		return testBg01Password;
	}

	/**
	 * @param testBg01Password the testBg01Password to set
	 */
	public void setTestBg01Password(String testBg01Password) {
		this.testBg01Password = testBg01Password;
	}

	/**
	 * @return the testISAQualifier
	 */
	public String getTestISAQualifier() {
		return testISAQualifier;
	}

	/**
	 * @param testISAQualifier the testISAQualifier to set
	 */
	public void setTestISAQualifier(String testISAQualifier) {
		this.testISAQualifier = testISAQualifier;
	}

	/**
	 * @return the testBg03ReceiverId
	 */
	public String getTestBg03ReceiverId() {
		return testBg03ReceiverId;
	}

	/**
	 * @param testBg03ReceiverId the testBg03ReceiverId to set
	 */
	public void setTestBg03ReceiverId(String testBg03ReceiverId) {
		this.testBg03ReceiverId = testBg03ReceiverId;
	}

	/**
	 * @return the testISAReceiverId
	 */
	public String getTestISAReceiverId() {
		return testISAReceiverId;
	}

	/**
	 * @param testISAReceiverId the testISAReceiverId to set
	 */
	public void setTestISAReceiverId(String testISAReceiverId) {
		this.testISAReceiverId = testISAReceiverId;
	}

	/**
	 * @return the testGSId
	 */
	public String getTestGSId() {
		return testGSId;
	}

	/**
	 * @param testGSId the testGSId to set
	 */
	public void setTestGSId(String testGSId) {
		this.testGSId = testGSId;
	}

	/**
	 * @return the tdccAnsiVersion
	 */
	public String getTdccAnsiVersion() {
		return tdccAnsiVersion;
	}

	/**
	 * @param tdccAnsiVersion the tdccAnsiVersion to set
	 */
	public void setTdccAnsiVersion(String tdccAnsiVersion) {
		this.tdccAnsiVersion = tdccAnsiVersion;
	}

	/**
	 * @return the fillersName
	 */
	public String getFillersName() {
		return fillersName;
	}

	/**
	 * @param fillersName the fillersName to set
	 */
	public void setFillersName(String fillersName) {
		this.fillersName = fillersName;
	}

	/**
	 * @return the fillersPhone
	 */
	public String getFillersPhone() {
		return fillersPhone;
	}

	/**
	 * @param fillersPhone the fillersPhone to set
	 */
	public void setFillersPhone(String fillersPhone) {
		this.fillersPhone = fillersPhone;
	}

	/**
	 * @return the fillerExtn
	 */
	public String getFillerExtn() {
		return fillerExtn;
	}

	/**
	 * @param fillerExtn the fillerExtn to set
	 */
	public void setFillerExtn(String fillerExtn) {
		this.fillerExtn = fillerExtn;
	}

	/**
	 * @return the sendCopyInMail
	 */
	public String getSendCopyInMail() {
		return sendCopyInMail;
	}

	/**
	 * @param sendCopyInMail the sendCopyInMail to set
	 */
	public void setSendCopyInMail(String sendCopyInMail) {
		this.sendCopyInMail = sendCopyInMail;
	}

	/**
	 * @return the fillersEmail
	 */
	public String getFillersEmail() {
		return fillersEmail;
	}

	/**
	 * @param fillersEmail the fillersEmail to set
	 */
	public void setFillersEmail(String fillersEmail) {
		this.fillersEmail = fillersEmail;
	}

	/**
	 * @return the isL2L
	 */
	public String getIsL2L() {
		return isL2L;
	}

	/**
	 * @param isL2L the isL2L to set
	 */
	public void setIsL2L(String isL2L) {
		this.isL2L = isL2L;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
