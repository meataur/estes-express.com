package com.estes.myestes.shipmentmanifest.dto;

import java.util.ArrayList;
import java.util.List;

import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModelProperty;

public class ManifestResponseDTO {
	
	@ApiModelProperty(notes="Error code/message")
	ServiceResponse error;
	@ApiModelProperty(notes="Table of information")
	List<ManifestRecordDTO> manifestRecords;
	@ApiModelProperty(notes="If paged request has more info for another page")
	Boolean hasNextPage;
	@ApiModelProperty(notes="If paged request has more info for previous page")
	Boolean hasPreviousPage;
	
	public ManifestResponseDTO() {
		manifestRecords = new ArrayList<ManifestRecordDTO>();
		hasNextPage = false;
		hasPreviousPage = false;
	}

	public ServiceResponse getError() {
		return error;
	}
	public void setError(ServiceResponse error) {
		this.error = error;
	}
	public Boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public Boolean getHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(Boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public void setManifestRecords(List<ManifestRecordDTO> records) {
		this.manifestRecords = records;
	}
	public List<ManifestRecordDTO> getManifestRecords() {
		return manifestRecords;
	}
	
}