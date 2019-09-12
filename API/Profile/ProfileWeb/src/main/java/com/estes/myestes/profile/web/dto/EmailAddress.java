package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.NotNull;

//import com.estes.myestes.profile.annotations.ValidEmail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="This model will be used to update user email address")
public class EmailAddress {
	
	@NotNull
	//@ValidEmail(message="Invalid email address")
	@ApiModelProperty(name="Email Address",notes="User new email Address")
	private String email;
}
