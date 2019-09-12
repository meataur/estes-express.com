package com.estes.dto.common;

import java.util.List;

public class GenericResponse {
	private List<ErrorMessage> errorList;
	public List<ErrorMessage> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<ErrorMessage> errorList) {
		this.errorList = errorList;
	}
}
