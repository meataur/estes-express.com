package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="General Information for Creating BOL / BOL Template/ BOL Draft")
@Data
public class GeneralInformation{

	@NotNull
	@ApiModelProperty(position=1, notes="Bill of Lading Number")
	private String bolNumber;

	@ApiModelProperty(position=2, notes="Bill of Lading Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	@JsonbProperty("date")
	private Date bolDate;

	@ApiModelProperty(position=3,notes="Assign Pro Number?")
	private boolean assignProNumber; 

	@ApiModelProperty(position=4,notes="Assigned PRO Number")
	private String proNumber;

	@ApiModelProperty(position=5,notes="Is Master BOL?")
	private boolean masterBol; 

	@ApiModelProperty(position=6,notes="Master BOL Number")
	private String masterBolNumber;

	@ApiModelProperty(position=7,notes="Guranteed")
	private boolean guranteed;

	@ApiModelProperty(position=8,notes="Existing Quote Number")
	private String quote;
	
	@ApiModelProperty(position=9,notes="I have read and agree to the Guaranteed Terms of Service.")
	private boolean tosChecked;

	@ApiModelProperty(position=10,notes="Guaranteed Pickup Request")
	private boolean pickupRequest;
}