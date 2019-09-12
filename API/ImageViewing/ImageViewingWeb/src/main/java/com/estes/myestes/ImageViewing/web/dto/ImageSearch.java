package com.estes.myestes.ImageViewing.web.dto;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="Image Request")
public class ImageSearch {
	@NotNull
	@ApiModelProperty(notes="Image Type/Document Type – BOL/DR/WR",required=true)
	private String documentType;
	
	@NotNull
	@ApiModelProperty(notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
	private String searchType;
	
	@NotNull
	@ApiModelProperty(notes="Search Criteria. (Note - Mutpliple should be space or new line separated.)")
	private String searchTerm;
	
	public List<String> getSearchCriteria(){
		return Arrays.asList(searchTerm.trim().split("\\s+"));
	}
}
