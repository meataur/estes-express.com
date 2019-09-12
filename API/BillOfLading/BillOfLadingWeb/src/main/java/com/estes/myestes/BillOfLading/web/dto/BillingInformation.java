package com.estes.myestes.BillOfLading.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Billing Information")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingInformation{

	@ApiModelProperty(position=1, notes="Bill-To")
	private BillTo billTo;

	@ApiModelProperty(position=2, notes="COD Remit-to")
	private boolean codRemitTo=false;

	@ApiModelProperty(position=3,notes="COD Remit-to Information")
	private CodRemitTo codRemitToInfo;
	
}