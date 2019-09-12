package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Basic User Profile - Authenticated user can update")
public class BasicProfile {

	 @NotNull(message="First Name is required")
	 @Size(min=1,message="First Name is required")
	 @Size(max=25, message="First Name can have maximum 25 characters")
	 @ApiModelProperty(position=1, notes="First Name")
	 private String firstName;
	 
	 @NotNull(message="Last Name is required")
	 @Size(min=1,message="Last Name is required")
	 @Size(max=25,message="Last Name can have maximum 25 characters")
	 @ApiModelProperty(position=2, notes="Last Name")	
	 private String lastName;
	 
	 @NotNull(message="Company Name is required")
	 @Size(min=1,message="Company Name is required")
	 @Size(max=50,message="Company Name can have maximum 50 characters")
	 @ApiModelProperty(position=3, notes="Company Name")
	 private String companyName;
	 
	 @NotNull(message="Phone Number is required")
	 @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid Phone Number")
	 @ApiModelProperty(position=4, notes="Phone Number")
	 private String phone;
	 
	 @ApiModelProperty(position=5, notes="Phone Number")
	 private boolean notify;
	 

	public void setFirstName(String firstName) {
		this.firstName = firstName != null? firstName.trim() : "";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName != null? lastName.trim() : "";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName != null? companyName.trim() : "";
	}
	 
	 
	 public String getAreaCode(){
		return phone.replaceAll("\\D+", "").length()< 10? "": phone.replaceAll("\\D+", "").substring(0, 3);
	 }
	 
	 public String getExchange(){
		return phone.replaceAll("\\D+", "").length()< 10? "": phone.replaceAll("\\D+", "").substring(3, 6);
	 }
	 
	 public String getLastFour(){
		return phone.replaceAll("\\D+", "").length()< 10? "": phone.replaceAll("\\D+", "").substring(6,10);
	 }

	public String getPreference() {
		return notify==true ? "Y" : "N";
	}
	 
}
