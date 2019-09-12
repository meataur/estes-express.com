package com.estes.myestes.profile.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="User Profile")
public class Profile
{
	 @ApiModelProperty(position=1, notes="First Name")
	 private String firstName;
	 
	 @ApiModelProperty(position=2, notes="Last Name")	
	 private String lastName;

	 @ApiModelProperty(position=3, notes="Username")
	 private String username;

	 @ApiModelProperty(position=4, notes="Email Address")
	 private String email;

	 @ApiModelProperty(position=5, notes="Phone Number")
	 private String phone;

	 @ApiModelProperty(position=6, notes="Company Name")
	 private String companyName;

	 @ApiModelProperty(position=7, notes="Account Type")
	 private String accountType;

	 @ApiModelProperty(position=8, notes="Account Code")
	 private String accountCode;

	 @ApiModelProperty(position=9, notes="Created Date")
	 private String createdDate;

	 @ApiModelProperty(position=10, notes="User Status: ENABLED/DISABLED")
	 private UserStatus status;
	 
	 @ApiModelProperty(position=11, notes="Email Update Preference. Y for Yes, N for No")
	 private String preference;
	 
}