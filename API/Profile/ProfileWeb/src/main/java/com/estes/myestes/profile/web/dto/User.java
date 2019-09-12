package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

//import com.estes.myestes.profile.annotations.FieldsMatch;
//import com.estes.myestes.profile.annotations.ValidEmail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
//@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
@ApiModel(description="To create Sub Account")
public class User {
	
	@NotNull
	//@Size(min=1, max=25)
	@ApiModelProperty(position=1, name="firstName",notes="First Name")
	private String firstName;
	
	@NotNull
	//@Size(min=1,max=25)
	@ApiModelProperty(position=2, name="lastName",notes="First Name")
	private String lastName;
	
	@ApiModelProperty(position=3,hidden=true, name="companyName",notes="Company Name")
	private String companyName;
	
	@ApiModelProperty(position=4,hidden=true, name="accountCode",notes="Account Code")
	private String accountCode;
	
	@ApiModelProperty(position=5,hidden=true, name="accountType",notes="Account Type")
	private String accountType;
	
	@NotNull
	//@ValidEmail
	@ApiModelProperty(position=5, name="email",notes="Email Address")
	private String email;
	
	@NotNull
	//@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone number")
	@ApiModelProperty(position=6, name="phone",notes="Phone Number")
	private String phone;
	
	@NotNull
	//@Size(min=5,max=10,message="Username should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(position=7,name="Username",notes="Username should have minimum 5 & maximum 10 characters")
	private String username;
	

	@NotNull(message = "Password can't be null")
	//@Size(min=5, max=10, message = "Password should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(position=8,notes="Password should have minimum 5 & maximum 10 characters. Password should match the confirm password")
	private String password;

	@NotNull
	//@Size(min =5, max=10, message = "Confirm Password should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(position=9,name="Confirm Password",notes="Confirm Password should have minimum 5 & maximum 10 characters. Confirm password should match the Password")
	private String confirmPassword;
	
	@NotNull
	@ApiModelProperty(position=10, name="notify",notes="Notify can be either 'Y' or 'N")
	private String notify;
	
	@AssertTrue(message="Notify can be either 'Y' or 'N'")
    public boolean isNotify() {
        return notify!=null && ( notify.trim().equalsIgnoreCase("Y") || notify.trim().equalsIgnoreCase("N"));
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

}