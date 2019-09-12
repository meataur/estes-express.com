package com.estes.myestes.BillOfLading.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Bill-To Information")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillTo {
	
	@ApiModelProperty(position=0, notes="Bill-To Option Value")
	private BillToRole payor;
	
	@ApiModelProperty(position=1, notes="Fee")
	private Fee fee;
	
	@ApiModelProperty(position=2, notes="It is required if role is Third Party or Other")
	private AddressInformation billingAddressInfo;

	public AddressInformation getBillingAddressInfo() {
		
		if(billingAddressInfo==null){
			billingAddressInfo = new AddressInformation();
		}

		return billingAddressInfo;
	}
	
}