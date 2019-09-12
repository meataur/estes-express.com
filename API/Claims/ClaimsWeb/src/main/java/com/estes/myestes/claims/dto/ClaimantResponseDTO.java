/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.util.List;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModelProperty;

/**
 * claimant response DTO
 */
public class ClaimantResponseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 47319545195093937L;
	@ApiModelProperty(notes="The claimant name")
	String name;
	@ApiModelProperty(notes="The claimant address")
	Address address;
	@ApiModelProperty(notes="The claimant email")
	String email;
	@ApiModelProperty(notes="The claimant phone - xxxxxxxxxx")
	String phone;
	@ApiModelProperty(notes="The claimant fax - xxxxxxxxxx")
	String fax;
	
	public String getName() {
		return name == null ? null : name.trim();
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email == null ? null : email.trim();
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone == null ? null : phone.trim();
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax == null ? null : fax.trim();
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
}