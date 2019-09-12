/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Info request DTO
 */
public class EmailClaimsRequestDTO extends ClaimsRequestDTO implements Serializable {
	private static final long serialVersionUID = 5359892133749186252L;
	@ApiModelProperty(notes="The email addresses seperated by \n")
	String emailAddresses;
	@ApiModelProperty(notes="File format of attachment – CSV/XLS/TXT")
	String format;
	
	public String getEmailAddresses() {
		return emailAddresses;
	}
	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

}