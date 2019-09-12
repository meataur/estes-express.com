package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

//import com.estes.myestes.profile.annotations.FieldsMatch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
//@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
@ApiModel(description="Password model will be used to change password")
public class Password {
	
	@NotNull
	//@Size(min=5, max=10, message = "Password should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(position=1,notes="Password should have minimum 5 & maximum 10 characters. Password should match the confirm password")
	private String password;

	@NotNull
	//@Size(min =5, max=10, message = "Confirm Password should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(position=2,name="Confirm Password",notes="Confirm Password should have minimum 5 & maximum 10 characters. Confirm password should match the Password")
	private String confirmPassword;
	
}
