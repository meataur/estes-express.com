/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.util.List;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModelProperty;

/**
 * Info response DTO
 */
public class ProInfoResponseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 47319545195093937L;
	@ApiModelProperty(notes="The shippers name")
	String shipperName;
	@ApiModelProperty(notes="The shippers address")
	Address shipperAddress;
	@ApiModelProperty(notes="The consignees name")
	String consigneeName;
	@ApiModelProperty(notes="The consignees address")
	Address consigneeAddress;
	@ApiModelProperty(notes="The BOL number assocated with this pro")
	String bolNum;
	@ApiModelProperty(notes="The PROs date – dd/mm/yyyy ")
	String proDate;
	@ApiModelProperty(notes="The BOLs date")
	String bolDate;
	
	public String getBolNum() {
		return bolNum == null ? null : bolNum.trim();
	}
	public void setBolNum(String bolNum) {
		this.bolNum = bolNum;
	}
	public String getProDate() {
		return proDate == null ? null : proDate.trim();
	}
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
	public String getBolDate() {
		return bolDate == null ? null : bolDate.trim();
	}
	public void setBolDate(String bolDate) {
		this.bolDate = bolDate;
	}
	public String getShipperName() {
		return shipperName == null ? null : shipperName.trim();
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public Address getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(Address shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getConsigneeName() {
		return consigneeName == null ? null : consigneeName.trim();
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public Address getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(Address consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	
}