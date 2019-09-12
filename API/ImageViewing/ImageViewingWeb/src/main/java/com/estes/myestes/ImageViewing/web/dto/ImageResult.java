package com.estes.myestes.ImageViewing.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author rahmaat
 */
@Data
@ApiModel(description="Image Result")
public class ImageResult {
	@ApiModelProperty(notes="Request Number")
	private String requestNumber;
	
	@ApiModelProperty(notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
	private String searchType;
	
	@ApiModelProperty(notes="Search Data: Formatted  PRO Number/Bill of Lading Number/Purchase Order Number/Interline PRO Number")
	private String searchData;
	
	@ApiModelProperty(notes="Search Key Value 1")
	private String key1;
	
	@ApiModelProperty(notes="Search Key Value 2")
	private String key2;
	
	@ApiModelProperty(notes="Search Key Value 3")
	private String key3;
	
	@ApiModelProperty(notes="Search Key Value 4")
	private String key4;
	
	@ApiModelProperty(notes="Search Key Value 5")
	private String key5;
	
	@ApiModelProperty(notes="Document type – BOL/DR/WR")
	private String docType;
	
	@ApiModelProperty(notes="Image Status: ERROR/WORKING/NOT_AVAILABLE/AVAILABLE/FAX_SENT/FAX_ERROR")
	private ImageRequestStatus status;
	
	@ApiModelProperty(notes="Image Error Message When status is ERROR, otherwise null.")
	private String errorMessage;
	
}
