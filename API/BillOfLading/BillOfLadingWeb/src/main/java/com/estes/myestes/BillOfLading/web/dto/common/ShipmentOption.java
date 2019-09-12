package com.estes.myestes.BillOfLading.web.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Shipment Option")
public class ShipmentOption {
	
	 @ApiModelProperty(position=1,value="Is Freeze?")
	 private boolean freeze;
	 
	 @ApiModelProperty(position=2,value="Is Oversized/Extreme Length?")
	 private boolean oversize;
	 
	 @ApiModelProperty(position=3,value="Is Food?")
	 private boolean food;
	 
	 @ApiModelProperty(position=4,value="Is Poision?")
	 private boolean poision;
	 
	 public String formatOptions(){
		 
		StringBuilder shipmentOption = new StringBuilder("YYYY");
		
		if(freeze==false){
			shipmentOption.insert(0, " ");
		}
		
		if(oversize==false){
			shipmentOption.insert(1, " ");
		}
		
		if(food==false){
			shipmentOption.insert(2, " ");
		}
		if(poision==false){
			shipmentOption.insert(3, " ");
		}
		
		return shipmentOption.toString();
	 }
}
