package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="COD Remit-To")
@Data
public class CodRemitTo{

	@ApiModelProperty(position=1, notes="Amount")
	private String amount;
	
	@ApiModelProperty(position=2, notes="Payment Type")
	private PaymentType paymentType;

	@ApiModelProperty(position=3, notes="Fee")
	private CodFee fee;

	@ApiModelProperty(position=4, notes="COD Remit-To Address Information")
	private AddressInformation codAddressInfo;
	
	
	
	
	public AddressInformation addressInfo(){
		if(codAddressInfo==null){
			return new AddressInformation();
		}
		return codAddressInfo;
	}
}