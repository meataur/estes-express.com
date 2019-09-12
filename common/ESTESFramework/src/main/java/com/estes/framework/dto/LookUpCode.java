package com.estes.framework.dto;
/**
 * 
 * @author SinghPa
 *
 */
public class LookUpCode {
   
    private long Id;
    private String code;
    private String value;
    private String description;
    
    public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
} 

