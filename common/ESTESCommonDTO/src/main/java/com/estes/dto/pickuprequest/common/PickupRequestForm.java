package com.estes.dto.pickuprequest.common;

import java.io.Serializable;

/**
 * Data transfer object used to exchange pickup request data to and from the web.
 * 
 */
public class PickupRequestForm implements Serializable {

	private static final long serialVersionUID = -1032539039267105613L;
	
	private UserInfo userInfo;
	private PartyInfoDTO shipperInfo;
	private PartyInfoDTO consigneeInfo;
	private PartyInfoDTO thirdParty;
	private PartyInfoDTO otherParty;
	private Shipment shipmentInfo;
	private Integer selectedWebGroupAccountRequest;
	private Integer selectedGroup91AccountRequest;
	private String shipperRoleValue;
	private boolean isTOSChecked;
	private String accountType;
	private String message;
	private String messageType;
	private String bolNumber;
	private String bolSeqNumber;
	private String templateName;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public PartyInfoDTO getShipperInfo() {
		return shipperInfo;
	}

	public void setShipperInfo(PartyInfoDTO shipperInfo) {
		this.shipperInfo = shipperInfo;
	}
		
	public Integer getSelectedWebGroupAccountRequest() {
		return selectedWebGroupAccountRequest;
	}

	public Shipment getShipmentInfo() {
		return shipmentInfo;
	}

	public void setShipmentInfo(Shipment shipmentInfo) {
		this.shipmentInfo = shipmentInfo;
	}

	public void setSelectedWebGroupAccountRequest(Integer selectedWebGroupAccountRequest) {
		this.selectedWebGroupAccountRequest = selectedWebGroupAccountRequest;
	}

	public Integer getSelectedGroup91AccountRequest() {
		return selectedGroup91AccountRequest;
	}

	public void setSelectedGroup91AccountRequest(Integer selectedGroup91AccountRequest) {
		this.selectedGroup91AccountRequest = selectedGroup91AccountRequest;
	}
	
	public String getShipperRoleValue() {
		return shipperRoleValue;
	}

	public void setShipperRoleValue(String shipperRoleValue) {
		this.shipperRoleValue = shipperRoleValue;
	}

	public boolean getIsTOSChecked() {
		return isTOSChecked;
	}

	public void setIsTOSChecked(boolean isTOSChecked) {
		this.isTOSChecked = isTOSChecked;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getBolNumber() {
		return bolNumber;
	}

	public void setBolNumber(String bolNumber) {
		this.bolNumber = bolNumber;
	}
	
	public String getBolSeqNumber() {
		return bolSeqNumber;
	}

	public void setBolSeqNumber(String bolSeqNumber) {
		this.bolSeqNumber = bolSeqNumber;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void markError(String messageType, String message) {
		setMessageType(messageType);
		setMessage(message);
	}
	
	public PartyInfoDTO getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(PartyInfoDTO thirdParty) {
		this.thirdParty = thirdParty;
	}

	public PartyInfoDTO getOtherParty() {
		return otherParty;
	}

	public void setOtherParty(PartyInfoDTO otherParty) {
		this.otherParty = otherParty;
	}

	public PartyInfoDTO getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(PartyInfoDTO consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}
	
	
}