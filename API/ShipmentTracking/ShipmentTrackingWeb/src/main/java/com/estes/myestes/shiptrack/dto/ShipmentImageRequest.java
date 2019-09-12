package com.estes.myestes.shiptrack.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Shipment tracking images request
 */
@ApiModel(description="Request for scanned image information asscoaited with a PRO")
@Data
public class ShipmentImageRequest {
	
	@ApiModelProperty(position=1, notes="PRO# : xxx-xxxxxxx")
	private String proNumber;
	@ApiModelProperty(position=2, notes="Request Number. Initially null or empty for first call. From second call UI should pass the request number got from the first call response.")
	private String requestNumber;
	
	public String getProNumber(){
		return proNumber.replaceAll("\\D+", "");
	}
	
	@ApiModelProperty(hidden=true)
	public int getOt(){
		return Integer.valueOf(this.getProNumber().substring(0,3));
	}
	
	
	@ApiModelProperty(hidden=true)
	public int getPro(){
		return Integer.valueOf(this.getProNumber().substring(3));
	}
}
