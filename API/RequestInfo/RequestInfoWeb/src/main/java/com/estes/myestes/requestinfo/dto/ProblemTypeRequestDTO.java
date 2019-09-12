package com.estes.myestes.requestinfo.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * problem type request DTO
 */
public class ProblemTypeRequestDTO {
	
	@ApiModelProperty(notes="OT PRO Number - xxx-xxxxxxx")
	String otPro;

	public String getOtPro() {
		return otPro;
	}

	public void setOtPro(String otPro) {
		this.otPro = otPro;
	}
}