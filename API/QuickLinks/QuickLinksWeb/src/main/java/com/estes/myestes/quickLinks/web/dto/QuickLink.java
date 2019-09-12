package com.estes.myestes.quickLinks.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Quick Link")
public class QuickLink
{
	@ApiModelProperty(notes="Application name")
	private String appName;
	@ApiModelProperty(notes="Description")
	private String description;
	@ApiModelProperty(notes="Application category")
	private String appCategory;
	@ApiModelProperty(notes="Link order for unauthenticated users;")
	private int linkOrder;
}
