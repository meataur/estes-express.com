/**
 *
 */

package com.estes.myestes.shipmentmanifest.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Email request DTO
 */
public class EmailRequestDTO extends ManifestRequestDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes="Format of email attachment - TXT/CSV/XLS")
	private String format;
	@ApiModelProperty(notes="Email addresses seperated by \n")
	private String email;
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}