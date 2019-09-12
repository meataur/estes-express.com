/**
 *
 */

package com.estes.myestes.recentshipments.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment DTO
 */
public class ShipmentDTO implements Serializable {
	private static final long serialVersionUID = -7818894247563096204L;
	
	@ApiModelProperty(notes="The OT")
	String ot;
	@ApiModelProperty(notes="The PRO")
	String pro;
	@ApiModelProperty(notes="The BOL")
	String bol;
	@ApiModelProperty(notes="The pickup date - YYYYMMDD")
	String pickupDate;
	@ApiModelProperty(notes="The delivery date - YYYYMMDD")
	String deliveryDate;
	@ApiModelProperty(notes="The recent shipments status")
	String status;
	@ApiModelProperty(notes="The consignee city")
	String consigneeCity;
	@ApiModelProperty(notes="The consignee state")
	String consigneeState;
	
	public String getOt() {
		return ot;
	}
	public void setOt(String ot) {
		this.ot = ot;
	}
	public String getPro() {
		return pro;
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getBol() {
		return bol;
	}
	public void setBol(String bol) {
		this.bol = bol;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConsigneeCity() {
		return consigneeCity;
	}
	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}
	public String getConsigneeState() {
		return consigneeState;
	}
	public void setConsigneeState(String consigneeState) {
		this.consigneeState = consigneeState;
	}

	
}