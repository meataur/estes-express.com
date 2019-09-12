/**
 *
 */

package com.estes.services.myestes.customer.dto;

import java.io.Serializable;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModelProperty;

/**
 * Customer account DTO
 */
public class AccountDTO implements Serializable
{
	private static final long serialVersionUID = 1259914759468533241L;
	
	@ApiModelProperty(notes="Customer account code/number")
	private String accountNumber;
	@ApiModelProperty(notes="Customer name")
	private String name;
	@ApiModelProperty(notes="Customer contact name")
	private String contactName;
	@ApiModelProperty(notes="Customer address")
	private Address address;
	@ApiModelProperty(notes="Customer phone nuumber")
	private String phone;
	
	@Override
	public String toString() {
		return "AccountDTO [accountNumber=" + accountNumber + ", name=" + name + ", contactName=" + contactName
				+ ", address=" + address + ", phone=" + phone + "]";
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone(){
		
		if(phone!=null){
			phone = phone.replaceAll("\\D+", "");
			if(phone.length() >10){
				phone = phone.substring(1);
			}else if(phone.length()<10){
				phone = null;
			}
			
			if(phone!=null){
				phone = "("+phone.substring(0, 3)+") "+phone.substring(3,6)+"-"+phone.substring(6);
			}
		}
		
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}