package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="This model will be used for updating username")
@Data
public class Username {
	@NotNull
	//@Size(min=5,max=10,message="Username should have minimum 5 & maximum 10 characters")
	@ApiModelProperty(name="Username",notes="Username should have minimum 5 & maximum 10 characters")
	private String username;
}
