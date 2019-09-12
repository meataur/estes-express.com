package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description="Shipment Class")
@Data
@NoArgsConstructor
public class ShipmentClass{
	@ApiModelProperty(position=1,notes="Shgipment Class Code")
	private String code;

	@ApiModelProperty(position=2,notes="Shipment Class Description")
	private String description;
	
	public ShipmentClass(String code){
		this.code = code;
	}
}