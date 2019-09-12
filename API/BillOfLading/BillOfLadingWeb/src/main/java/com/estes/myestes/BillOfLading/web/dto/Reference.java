package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Reference Number")
@Data
public class Reference{
	
	@ApiModelProperty(position=0, notes="Reference Sequence")
	private String referenceId;
	
	@ApiModelProperty(position=1,notes="Reference Number")
	private String referenceNumber;

	@ApiModelProperty(position=2,notes="Reference Type")
	private ReferenceType referenceType;

	@ApiModelProperty(position=3,notes="Cartoons")
	private String cartoon;

	@ApiModelProperty(position=4,notes="Weight")
	private String weight;
}

