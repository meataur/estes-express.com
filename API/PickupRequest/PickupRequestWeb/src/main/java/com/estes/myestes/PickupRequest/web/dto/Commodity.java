package com.estes.myestes.PickupRequest.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Commodity Information")
public class Commodity {
	
	@ApiModelProperty(position=1, value="Destination Zip Code")
	private String destinationZipCode;
	
	@ApiModelProperty(position=2,value="Destination Zip Code Extention")
	private String destinationZipCodeExt;
	
	@ApiModelProperty(position=3,value="Total Pieces")
	private int totalPieces;
	
	@ApiModelProperty(position=4,value="Total Weight")
	private int totalWeight;
	
	@ApiModelProperty(position=5,value="Total Skids")
	private int totalSkids;
	
	@ApiModelProperty(position=6,value="Is Hazmat?")
	private boolean hazmat;
	
	@ApiModelProperty(position=7,value="Special Instruction")
	private String specialInstructions;
	
	@ApiModelProperty(position=8,value="Reference Number")
	private String referenceNumber;
}
