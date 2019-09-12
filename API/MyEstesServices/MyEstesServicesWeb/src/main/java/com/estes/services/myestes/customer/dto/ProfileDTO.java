package com.estes.services.myestes.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="User Profile")
public class ProfileDTO
{
	public enum UserStatus {
		ENABLED,
		DISABLED
	}

	@ApiModelProperty(position=1, notes="Username")
	private String username;

	@ApiModelProperty(position=2, notes="Account Code")
	private String accountCode;

	@ApiModelProperty(position=3, notes="Account Type")
	private String accountType;

	@ApiModelProperty(position=4, notes="Company Name")
	private String companyName = "";

	@ApiModelProperty(position=5, notes="First Name")
	private String firstName = "";

	@ApiModelProperty(position=6, notes="Last Name")	
	private String lastName = "";

	@ApiModelProperty(position=7, notes="Email Address")
	private String email = "";

	@ApiModelProperty(position=8, notes="Phone Number")
	private String phone = "";

	@ApiModelProperty(position=9, notes="Email Update Preference. Y for Yes, N for No")
	private String preference = "";

	@ApiModelProperty(position=10, notes="Created Date")
	private String createdDate;

	@ApiModelProperty(position=11, notes="User Status: ENABLED/DISABLED")
	private UserStatus status;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		if(phone==null){
			return "";
		}
		
		phone = phone.replaceAll("\\D+", "");
		
		if(phone.length() < 10){
			return "";
		}
		
		return "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-" +phone.substring(6);
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

}