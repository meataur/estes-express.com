package com.estes.dto.pickuprequest.common;

/**
 * 
 * @author threska
 */
public class UserInfo {
	
	private String userId;
	private String account;
	private String accountType;
	private String role;
	private Integer selectedPreviousUserRequest;
	private String name;
	private String firstName;
	private String lastName;
	private String phone;
	private String phoneExt;
	private String emailAddress;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Integer getSelectedPreviousUserRequest() {
		return selectedPreviousUserRequest;
	}
	
	public void setSelectedPreviousUserRequest(Integer selectedPreviousUserRequest) {
		this.selectedPreviousUserRequest = selectedPreviousUserRequest;
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