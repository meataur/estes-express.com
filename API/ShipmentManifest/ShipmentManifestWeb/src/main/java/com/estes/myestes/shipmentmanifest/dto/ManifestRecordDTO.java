package com.estes.myestes.shipmentmanifest.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.edps.format.StringFormat;
import com.estes.dto.common.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class ManifestRecordDTO {
	
	@ApiModelProperty(notes="PRO Number")
	String proNumber;
	@JsonIgnore
	String pro;
	@JsonIgnore
	String proNumNoDash;
	@ApiModelProperty(notes="Bill Of Lading")
	String bol;
	@ApiModelProperty(notes="Purchase Order Number")
	String purchaseOrder;
	@ApiModelProperty(notes="Pickup Date YYYYMMDD")
	String pickupDate;
	@ApiModelProperty(notes="Delivery Date MM/DD/YYYY")
	String deliveryDate;
	@ApiModelProperty(notes="Origin")
	String origin;
	@JsonIgnore
	String originTerminalId;
	@ApiModelProperty(notes="Destination")
	String destination;
	@ApiModelProperty(notes="Pieces")
	String pieces;
	@ApiModelProperty(notes="Weight")
	String weight;
	@ApiModelProperty(notes="Charges")
	String charges;
	@ApiModelProperty(notes="Status")
	String status;
	@ApiModelProperty(notes="Received By")
	String receivedBy;
	@ApiModelProperty(notes="Date of delivery attempt MM/DD/YYYY")
	String firstDeliveryAttempt;
	@ApiModelProperty(notes="Time of delivery HH:MM AM/PM")
	String deliveryTime;
	@ApiModelProperty(notes="Date of appointment MM/DD/YYYY")
	String appointmentDate;
	@ApiModelProperty(notes="Time of appointment HH:MM AM/PM")
	String appointmentTime;
	@ApiModelProperty(notes="The shipper name")
	String shipper;
	@ApiModelProperty(notes="The shipper address")
	Address shipperAddress;
	@ApiModelProperty(notes="The consignee name")
	String consignee;
	@ApiModelProperty(notes="The consignee address")
	Address consigneeAddress;
	@ApiModelProperty(notes="Destination terminal")
	String destinationTerminal;
	@ApiModelProperty(notes="Phone number (xxx) xxx-xxxx")
	String phoneNumber;
	@ApiModelProperty(notes="Fax number (xxx) xxx-xxxx")
	String faxNumber;
	@JsonIgnore
	String billToCode;
	
	public String getProNumber() {
		return proNumber == null ? null:proNumber.trim();
	}
	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}
	public String getPro() {
		return pro == null ? null:pro.trim();
	}
	public void setPro(String pro) {
		this.pro = pro;
	}
	public String getProNumNoDash() {
		return proNumNoDash == null ? null:proNumNoDash.trim();
	}
	public void setProNumNoDash(String proNumNoDash) {
		this.proNumNoDash = proNumNoDash;
	}
	public String getBol() {
		return bol == null ? null:bol.trim();
	}
	public void setBol(String bol) {
		this.bol = bol;
	}
	public String getPurchaseOrder() {
		return purchaseOrder == null ? null:purchaseOrder.trim();
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getPickupDate() {
		return pickupDate == null ? null:pickupDate.trim();
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getDeliveryDate() {
		return deliveryDate == null ? null:deliveryDate.trim();
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getOrigin() {
		return origin == null ? null:origin.trim();
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOriginTerminalId() {
		return originTerminalId == null ? null:originTerminalId.trim();
	}
	public void setOriginTerminalId(String originTerminalId) {
		this.originTerminalId = originTerminalId;
	}
	public String getDestination() {
		return destination == null ? null:destination.trim();
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPieces() {
		return pieces == null ? null:pieces.trim();
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getWeight() {
		return weight == null ? null:weight.trim();
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCharges() {
		return charges == null ? null:charges.trim();
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public String getFirstDeliveryAttempt() {
		return firstDeliveryAttempt == null ? null:firstDeliveryAttempt.trim();
	}
	public void setFirstDeliveryAttempt(String firstDeliveryAttempt) {
		this.firstDeliveryAttempt = firstDeliveryAttempt;
	}
	public String getDeliveryTime() {
		return deliveryTime == null ? null:deliveryTime.trim();
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getAppointmentDate() {
		return appointmentDate == null ? null:appointmentDate.trim();
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getAppointmentTime() {
		return appointmentTime == null ? null:appointmentTime.trim();
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getShipper() {
		return shipper == null ? null:shipper.trim();
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}	
	public String getConsignee() {
		return consignee == null ? null:consignee.trim();
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getDestinationTerminal() {
		return destinationTerminal == null ? null:destinationTerminal.trim();
	}
	public void setDestinationTerminal(String destinationTerminal) {
		this.destinationTerminal = destinationTerminal;
	}
	public String getPhoneNumber() {
		return phoneNumber == null ? null:phoneNumber.trim();
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber == null ? null:faxNumber.trim();
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getStatus() {
		return status == null ? null:status.trim();
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReceivedBy() {
		return receivedBy == null ? null:receivedBy.trim();
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	public Address getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(Address shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public Address getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(Address consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getBillToCode() {
		return billToCode == null ? null:billToCode.trim();
	}
	public void setBillToCode(String billToCode) {
		this.billToCode = billToCode;
	}
	
	public String[] getManifestAsRow(Boolean withCharges) {
		// format pickup date
		String pickupDate;
		if ((this.getPickupDate() != null && !this.getPickupDate().trim().equals("")) && this.getPickupDate().length() == 8) {
			SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
			Date dateField = new Date();
			try {
				dateField = ymd.parse(this.getPickupDate());
				pickupDate = mdy.format(dateField);
			} catch (ParseException e) {
				pickupDate = "";
			}
		}
		else {
			pickupDate = "";
		}
		
		String[] arrayWithCharges = {
				this.getProNumber(),
				this.getBol(),
				this.getPurchaseOrder(),
				pickupDate,
				this.getDeliveryDate(),
				this.getOrigin(),
				this.getDestination(),
				this.getPieces(),
				this.getWeight(),
				this.getCharges(),
				this.getStatus(),
				this.getReceivedBy(),
				this.getFirstDeliveryAttempt(),
				this.getDeliveryTime(),
				this.getAppointmentDate(),
				this.getAppointmentTime(),
				this.getShipper(),
				this.getShipperAddress().getStreetAddress(),
				this.getShipperAddress().getStreetAddress2(),
				this.getShipperAddress().getCity(),
				this.getShipperAddress().getState(),
				this.getShipperAddress().getZip(),
				this.getConsignee(),
				this.getConsigneeAddress().getStreetAddress(),
				this.getConsigneeAddress().getStreetAddress2(),
				this.getConsigneeAddress().getCity(),
				this.getConsigneeAddress().getState(),
				this.getConsigneeAddress().getZip(),
				this.getDestinationTerminal(),
				this.getPhoneNumber(),
				this.getFaxNumber()
		};
		
		String[] arrayWithOutCharges = {
				this.getProNumber(),
				this.getBol(),
				this.getPurchaseOrder(),
				pickupDate,
				this.getDeliveryDate(),
				this.getOrigin(),
				this.getDestination(),
				this.getPieces(),
				this.getWeight(),
				this.getStatus(),
				this.getReceivedBy(),
				this.getFirstDeliveryAttempt(),
				this.getDeliveryTime(),
				this.getAppointmentDate(),
				this.getAppointmentTime(),
				this.getShipper(),
				this.getShipperAddress().getStreetAddress(),
				this.getShipperAddress().getStreetAddress2(),
				this.getShipperAddress().getCity(),
				this.getShipperAddress().getState(),
				this.getShipperAddress().getZip(),
				this.getConsignee(),
				this.getConsigneeAddress().getStreetAddress(),
				this.getConsigneeAddress().getStreetAddress2(),
				this.getConsigneeAddress().getCity(),
				this.getConsigneeAddress().getState(),
				this.getConsigneeAddress().getZip(),
				this.getDestinationTerminal(),
				this.getPhoneNumber(),
				this.getFaxNumber()
		};
		
		if(withCharges) {
			return arrayWithCharges;
		}
		else {
			return arrayWithOutCharges;
		}
	}
}

