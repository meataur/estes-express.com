package com.estes.myestes.wrinquiry.web.dto;


import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="Weight & Research Inquiry Search Request")
public class WRSearchRequest {

	@ApiModelProperty(notes = "Account Number if the account is national account")
	private String accountNumber;

	@NotNull
	@ApiModelProperty(notes = "Search By : PRO Number, Date Range,Other")
	private String searchBy;

	@ApiModelProperty(notes = "Search Criteria (Add Multiple PRO Numbers or BOL Numbers or PO Numbers with comma separated")
	private List<String> searchTerm;

	@ApiModelProperty(notes = "Start Date :  MM/DD/YYYY")
	private String startDate;

	@ApiModelProperty(notes="End Date : MM/DD/YYYY")
	 private String endDate;
	
	
	
	

}
