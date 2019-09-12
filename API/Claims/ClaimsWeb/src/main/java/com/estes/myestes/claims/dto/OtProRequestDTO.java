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
public class OtProRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8541296556221981960L;
	@ApiModelProperty(notes="If logged in as grouped account have to specify sub account from sub account service")
	String accountNumber;
	@ApiModelProperty(notes="OT PRO Number - XXX-XXXXXXX")
	String otpro;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getOtpro() {
		return otpro;
	}
	public void setOtpro(String otpro) {
		this.otpro = otpro;
	}

}