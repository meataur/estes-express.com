package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@ApiModel(description="BOL")
@Data
public class Bol{
	@ApiModelProperty(position=0, notes="BOL id/number/sequence")
	private int bolId;
	
	@ApiModelProperty(position=1, notes="Bol Number")
	private String bolNumber;
	
	@ApiModelProperty(position=2, notes="BOL date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	private Date bolDate;

	@JsonIgnore
	@ApiModelProperty(position=3, hidden=true, notes="PRO OT")
	private String proOt;
	
	@JsonIgnore
	@ApiModelProperty(position=3, hidden=true, notes="PRO Number Last Part")
	private String proNum;
	
	@ApiModelProperty(position=3, notes="PRO number")
	private String proNumber;
	
	@ApiModelProperty(position=4, notes="Shipper First Name.")
	private String shipperFirstName;
	
	@ApiModelProperty(position=5, notes="Shipper Last Name.")
	private String shipperLastName;
	
	@ApiModelProperty(position=6, notes="Shipper Company Name.")
	private String shipperCompanyName;
	

	@ApiModelProperty(position=7, notes="Consignee First Name.")
	private String consigneeFirstName;
	
	@ApiModelProperty(position=8, notes="Consignee Last Name.")
	private String consigneeLastName;
	
	@ApiModelProperty(position=9, notes="Consignee Company Name.")
	private String consigneeCompanyName;
	
	@JsonIgnore
	@ApiModelProperty(position=10, hidden=true, notes="Shipping Label Type")
	private String shippingLabelType;
	
	@JsonIgnore
	@ApiModelProperty(position=11, hidden=true, notes="Shipping Label Start")
	private String shippingLabelStart;
	
	@JsonIgnore
	@ApiModelProperty(position=12, hidden=true, notes="Shipping Label Total")
	private String shippingLabelTotal;
	
	@ApiModelProperty(position=13, notes="Shipping Labels. If the shipping label has already created, the value is true to show the view link. ELse the value is false to show the create link")
	private boolean hasShippingLabel;

}