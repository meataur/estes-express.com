package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Application")
public class App{
	
	@NotNull
	@ApiModelProperty(position=1,notes="Application Name")
	private String name;
	
	@ApiModelProperty(position=2,notes="Application Category")
	private String category;
	
	@ApiModelProperty(position=3,notes="Description")
	private String description;
	
}
