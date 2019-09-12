package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Shipping Label")
@Data
public class ShippingLabel{
	
	@ApiModelProperty(position=1,notes="Label Type : Possible value [0,1,2, 4,6]")
	private String labelType;

	@ApiModelProperty(position=2,notes="Start Label Position With : ")
	private String startLabel;

	@ApiModelProperty(position=3,notes="Number of Labels")
	private String numberOfLabel;
}