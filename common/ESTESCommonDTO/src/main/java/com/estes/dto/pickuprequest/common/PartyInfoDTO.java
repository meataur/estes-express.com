package com.estes.dto.pickuprequest.common;
/**
 * 
 * @author singhpa
 *
 */
public class PartyInfoDTO {
	
	private String company;
	private String accountCode;
	private String name;
	private String firstName;
	private String lastName;
	private String phone;
	private String phoneExt;
	private String emailAddress;
	protected String addressLine1;
	protected String addressLine2;
	protected String city;
	protected String state;
	protected String zipCode5;
	protected String zipCode10;
	
	private Integer selectedPreviousShipperRequest;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public Integer getSelectedPreviousShipperRequest() {
		return selectedPreviousShipperRequest;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode5() {
		return zipCode5;
	}

	public void setZipCode5(String zipCode5) {
		this.zipCode5 = zipCode5;
	}

	public String getZipCode10() {
		return zipCode10;
	}

	public void setZipCode10(String zipCode10) {
		this.zipCode10 = zipCode10;
	}

	public void setSelectedPreviousShipperRequest(Integer selectedPreviousShipperRequest) {
		this.selectedPreviousShipperRequest = selectedPreviousShipperRequest;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}