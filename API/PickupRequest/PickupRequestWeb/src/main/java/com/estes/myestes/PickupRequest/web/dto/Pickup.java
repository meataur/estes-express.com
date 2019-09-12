package com.estes.myestes.PickupRequest.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Pickup History")
@Data
public class Pickup {
	
	@ApiModelProperty(value="Request Number")
	private String  requestNumber;
	
	@ApiModelProperty(value="PRO Number")
	private String proNumber;
	
	@ApiModelProperty(value="Shipper Company")
	private String shipperCompany;
	
	@ApiModelProperty(value="Shipper City")
	private String shipperCity;
	
	@ApiModelProperty(value="Shipper State")
	private String shipperState;
	
	@ApiModelProperty(value="Submitted Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date submittedDate;
	
	@ApiModelProperty(value="Pickup Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date pickupDate;
	
	@ApiModelProperty(value="Total Pieces")
	private int totalPieces;
	
	@ApiModelProperty(value="Total Weight")
	private int totalWeight;
	
	@ApiModelProperty(value="Terminal Name")
	private String terminalName;
	
	@ApiModelProperty(value="Terminal Phone - (xxx) xxx-xxxx")
	private String terminalPhone;
	
	@ApiModelProperty(value="Terminal Fax - (xxx) xxx-xxxx")
	private String terminalFax;
	
	
	@ApiModelProperty(value="Status")
	private String status;
	
	public void setSubmittedDate(Date date){
		submittedDate = new Date(date.getTime());
	}
	
	public String getTerminalPhone(){
		if(terminalPhone==null){
			return "";
		}
		
		terminalPhone = terminalPhone.replaceAll("\\D+", "");
		

		if(terminalPhone.length() < 10){
			return "";
		}
		if(terminalPhone.length() > 10){
			terminalPhone = terminalPhone.substring(1);
		}
		
		return "("+terminalPhone.substring(0, 3)+") "+terminalPhone.substring(3, 6)+"-" +terminalPhone.substring(6);
		
	}
	
	public String getTerminalFax(){
		if(terminalFax==null){
			return "";
		}
		
		terminalFax = terminalFax.replaceAll("\\D+", "");
		
		if(terminalFax.length() < 10){
			return "";
		}
		if(terminalFax.length() > 10){
			terminalFax = terminalFax.substring(1);
		}
		
		return "("+terminalFax.substring(0, 3)+") "+terminalFax.substring(3, 6)+"-" +terminalFax.substring(6);
		
	}
}
