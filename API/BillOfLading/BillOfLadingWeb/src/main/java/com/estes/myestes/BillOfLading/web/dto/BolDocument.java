package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Shipping Label Document for a BOL")
@Data
public class BolDocument {
	@ApiModelProperty(position=0,notes="BOL Id")
	private int bolId;
	
	
	@ApiModelProperty(position=1, notes="Document URL")
	private String documentUrl;
	
	
	@ApiModelProperty(position=2, notes="Document Type: [Shipping Label, BOL]")
	private String documentType;
	

}
