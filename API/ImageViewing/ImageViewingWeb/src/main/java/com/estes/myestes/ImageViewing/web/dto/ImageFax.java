package com.estes.myestes.ImageViewing.web.dto;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Image Request")
public class ImageFax {
	
	@NotNull
	@ApiModelProperty(position=1, example="DR", notes="Image Type/Document Type – BOL/DR/WR",required=true)
	private String documentType;
	
	@NotNull
	@ApiModelProperty(position=2, required=true, example="F", notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
	private String searchType;

	@NotNull
	@ApiModelProperty(position=3, example="037-7688929",notes="Pro Number is composed of key1 & key2 by hyphen/dash(-) separated.")
	private List<String> proNumbers;
	
	@ApiModelProperty(notes="Fax Information")
	@NotNull
	@Valid
	private FaxInfo faxInfo;
}
