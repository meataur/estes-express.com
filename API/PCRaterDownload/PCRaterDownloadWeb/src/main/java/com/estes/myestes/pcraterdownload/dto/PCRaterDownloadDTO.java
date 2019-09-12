/**
 * @author:Pradeep K
 *
 */

package com.estes.myestes.pcraterdownload.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes customer PCRater download
 */
public class PCRaterDownloadDTO
{
	@ApiModelProperty(notes="Contact Name")
	private String contactName ="";
	@ApiModelProperty(notes = "Company Name")
	private String companyName = "";
	@ApiModelProperty(notes = "Estes account code")
	private String accountCode;
	@ApiModelProperty(notes="Customer address")
	private String customerAddress="";
	@ApiModelProperty(notes="Customer city")
	private String customerCity="";
	@ApiModelProperty(notes="Customer state")
	private String customerState="";
	@ApiModelProperty(notes="Customer zip")
	private String customerZip="";	
	@ApiModelProperty(notes="z4")
	private String z4="";	
	@ApiModelProperty(notes="Customer phone – (xxx) xxx-xxxx")
	private String customerPhone;
	@ApiModelProperty(notes="Phone extension if any")
	private String extension = "";
	@ApiModelProperty(notes="Contact email")
	private String email = "";
	@ApiModelProperty(notes="willing to be e-mailed")
	private boolean isEmailReceptive;
	@ApiModelProperty(notes="willing to be contacted by a salesperson")
	private boolean isSalespersonReceptive;


	public PCRaterDownloadDTO() {
		super();
	}


	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}


	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}


	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}


	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}


	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}


	/**
	 * @return the customerCity
	 */
	public String getCustomerCity() {
		return customerCity;
	}


	/**
	 * @param customerCity the customerCity to set
	 */
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}


	/**
	 * @return the customerState
	 */
	public String getCustomerState() {
		return customerState;
	}


	/**
	 * @param customerState the customerState to set
	 */
	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}


	/**
	 * @return the customerZip
	 */
	public String getCustomerZip() {
		return customerZip;
	}


	/**
	 * @param customerZip the customerZip to set
	 */
	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}
	
	/**
	 * @return the z4
	 */
	public String getZ4() {
		return z4;
	}


	/**
	 * @param z4 the z4 to set
	 */
	public void setZ4(String z4) {
		this.z4 = z4;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}


	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}


	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}


	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the isEmailReceptive
	 */
	public boolean isEmailReceptive() {
		return isEmailReceptive;
	}


	/**
	 * @param isEmailReceptive the isEmailReceptive to set
	 */
	public void setEmailReceptive(boolean isEmailReceptive) {
		this.isEmailReceptive = isEmailReceptive;
	}


	/**
	 * @return the isSalespersonReceptive
	 */
	public boolean isSalespersonReceptive() {
		return isSalespersonReceptive;
	}


	/**
	 * @param isSalespersonReceptive the isSalespersonReceptive to set
	 */
	public void setSalespersonReceptive(boolean isSalespersonReceptive) {
		this.isSalespersonReceptive = isSalespersonReceptive;
	}
 
}
