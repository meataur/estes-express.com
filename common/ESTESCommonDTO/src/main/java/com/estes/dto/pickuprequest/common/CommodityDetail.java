package com.estes.dto.pickuprequest.common;

import java.util.List;

/**
 * Domain object which represents a shipment request for a pickup request.
 * @author singhpa
 *
 */
public class CommodityDetail {
	
	private String destinationZipCode;
	private String destinationZipCodeExt;
	private String totalPieces;
	private String totalWeight;
	private String totalSkids;
	private String hazmat;
	private String specialInstructions;
	private String referenceNumber;
	
	private List<String> shipmentOptionList;
	private String serviceLevel;
	private boolean isNotifyByEmailAccepted, isNotifyByEmailCompleted;
	private boolean isActive;
	private Integer selectedPreviousRequestIndex;
	private String requestNumber;
	
	private List<PartyNotificationDetail> partyNotificationDetailList;
	
	public String getDestinationZipCode() {
		return destinationZipCode;
	}

	public void setDestinationZipCode(String destinationZipCode) {
		this.destinationZipCode = destinationZipCode;
	}

	public String getDestinationZipCodeExt() {
		return destinationZipCodeExt;
	}

	public void setDestinationZipCodeExt(String destinationZipCodeExt) {
		this.destinationZipCodeExt = destinationZipCodeExt;
	}

	public String getTotalPieces() {
		return totalPieces;
	}

	public void setTotalPieces(String totalPieces) {
		this.totalPieces = totalPieces;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalSkids() {
		return totalSkids;
	}

	public void setTotalSkids(String totalSkids) {
		this.totalSkids = totalSkids;
	}

	public String getHazmat() {
		return hazmat;
	}

	public void setHazmat(String hazmat) {
		this.hazmat = hazmat;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setNotifyByEmailAccepted(boolean isNotifyByEmailAccepted) {
		this.isNotifyByEmailAccepted = isNotifyByEmailAccepted;
	}

	public void setNotifyByEmailCompleted(boolean isNotifyByEmailCompleted) {
		this.isNotifyByEmailCompleted = isNotifyByEmailCompleted;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<String> getShipmentOptionList() {
		return shipmentOptionList;
	}

	public void setShipmentOptionList(List<String> shipmentOptionList) {
		this.shipmentOptionList = shipmentOptionList;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}
	
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public boolean getIsNotifyByEmailAccepted() {
		return isNotifyByEmailAccepted;
	}

	public void setIsNotifyByEmailAccepted(boolean isNotifyByEmailAccepted) {
		this.isNotifyByEmailAccepted = isNotifyByEmailAccepted;
	}
	
	public boolean getIsNotifyByEmailCompleted() {
		return isNotifyByEmailCompleted;
	}

	public void setIsNotifyByEmailCompleted(boolean isNotifyByEmailCompleted) {
		this.isNotifyByEmailCompleted = isNotifyByEmailCompleted;
	}
	
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Integer getSelectedPreviousRequestIndex() {
		return selectedPreviousRequestIndex;
	}

	public void setSelectedPreviousRequestIndex(Integer selectedPreviousRequestIndex) {
		this.selectedPreviousRequestIndex = selectedPreviousRequestIndex;
	}
	
	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public List<PartyNotificationDetail> getPartyNotificationDetailList() {
		return partyNotificationDetailList;
	}

	public void setPartyNotificationDetailList(List<PartyNotificationDetail> partyNotificationDetailList) {
		this.partyNotificationDetailList = partyNotificationDetailList;
	}	
	
}