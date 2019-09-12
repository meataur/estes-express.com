package com.estes.dto.common;

public class ErrorMessage {
	private String fieldName;
	private String message;
	private String code;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "ErrorMessage [fieldName=" + fieldName + ", message=" + message
				+ ", code=" + code + "]";
	}
}
