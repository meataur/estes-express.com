/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.dto;

import java.io.Serializable;

import com.estes.dto.common.Address;
import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment tracking request
 */
@ApiModel(description="Shipment information")
public class Shipment implements Serializable
{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(position=1, notes="Error code/message")
	ServiceResponse errorInfo;
	@ApiModelProperty(notes="Shipment type")
	String type;
	@ApiModelProperty(notes="Estes shipment PRO")
	String pro;
	@ApiModelProperty(notes="Error message")
	String error;
	@ApiModelProperty(notes="Bill-to account code/number")
	String billTo;
	@ApiModelProperty(notes="Shipper account code/number")
	String shipper;
	@ApiModelProperty(notes="Consignee account code/number")
	String cons;
	@ApiModelProperty(notes="Control account code/number")
	String control;
	@ApiModelProperty(notes="Payor account code/number")
	String payor;
	@ApiModelProperty(notes="Terms code")
	String terms;
	@ApiModelProperty(notes="Shipment charges")
	String charges;
	@ApiModelProperty(notes="Delivery date")
	String deliveryDate;
	@ApiModelProperty(notes="Delivery time")
	String deliveryTime;
	@ApiModelProperty(notes="Received by")
	String receivedBy;
	@ApiModelProperty(notes="Pickup date")
	String pickupDate;
	@ApiModelProperty(notes="Pickup time")
	String pickupTime;
	@ApiModelProperty(notes="Shipment weight")
	String weight;
	@ApiModelProperty(notes="Number of pieces")
	String pieces;
	@ApiModelProperty(notes="Shipper name")
	String shipperName;
	@ApiModelProperty(notes="Shipper address")
	Address shipperAddress;
	@ApiModelProperty(notes="Shipper reference number")
	String shipperRefnum;
	@ApiModelProperty(notes="Consignee name")
	String consName;
	@ApiModelProperty(notes="Consignee address")
	Address consAddress;
	@ApiModelProperty(notes="Consignee reference number")
	String consRefnum;
	@ApiModelProperty(notes="Third party name")
	String thirdPartyName;
	@ApiModelProperty(notes="Third party address")
	Address thirdPartyAddress;
	@ApiModelProperty(notes="Third party reference number")
	String thirdPartyRefnum;
	@ApiModelProperty(notes="Service type code")
	String serviceType;
	@ApiModelProperty(notes="Appointment date")
	String apptDate;
	@ApiModelProperty(notes="Appointment time")
	String apptTime;
	@ApiModelProperty(notes="Appointment status")
	String apptStatus;
	@ApiModelProperty(notes="Destination terminal abbreviation")
	String destTerminalNum;
	@ApiModelProperty(notes="Destination terminal name")
	String destTerminalName;
	@ApiModelProperty(notes="Destination terminal address")
	Address destTerminalAddress;
	@ApiModelProperty(notes="Destination terminal phone number")
	String destTerminalPhone;
	@ApiModelProperty(notes="Destination terminal fax number")
	String destTerminalFax;
	@ApiModelProperty(notes="Bill of lading number")
	String bol;
	@ApiModelProperty(notes="Purchase order number")
	String poNum;
	@ApiModelProperty(notes="Load number")
	String loadNum;
	@ApiModelProperty(notes="Interline freight bill number")
	String ilfb;
	@ApiModelProperty(notes="Interline SCAC")
	String ilScac;
	@ApiModelProperty(notes="Interline name")
	String ilName;
	@ApiModelProperty(notes="Interline type")
	String ilType;
	@ApiModelProperty(notes="1st delivery attempt date")
	String firstDeliveryAttempt;
	@ApiModelProperty(notes="Status")
	String status;
	@ApiModelProperty(notes="Status timestamp")
	String statusTime;
	@ApiModelProperty(notes="Estimated delivery date")
	String estDelDate;
	@ApiModelProperty(notes="Estimated time of arrival")
	String estArrivalTime;
	@ApiModelProperty(notes="Dimensional weight")
	String dimWeight;
	@ApiModelProperty(notes="Master original terminal")
	String masterOT;
	@ApiModelProperty(notes="Master PRO number")
	String masterProNum;
	@ApiModelProperty(notes="Freight charge disclaimer")
	String freightChargeDisc;
	@ApiModelProperty(notes="Estimated delivery date disclaimer")
	String estDelDateDisc;
	@ApiModelProperty(notes="Delivery date disclaimer")
	String delDateDisc;
	@ApiModelProperty(notes="Delivery time disclaimer")
	String delTimeDisc;
	@ApiModelProperty(notes="Freight charge audit text")
	String freightChargeAudit;
	@ApiModelProperty(notes="Last changed timestamp")
	String lastChanged;
	@ApiModelProperty(notes="Is customer the payor - Y/N")
	String isPayor;
	@ApiModelProperty(notes="Is customer party to shipment - Y/N")
	String isParty;
	@ApiModelProperty(notes="Compare string for sort")
	String sortCompare;

	public Shipment()
	{
		this.errorInfo = new ServiceResponse();
		this.shipperAddress = new Address();
		this.consAddress = new Address();
		this.thirdPartyAddress = new Address();
		this.destTerminalAddress= new Address();
		this.destTerminalAddress.setStreetAddress2("");
		this.destTerminalAddress.setCountry("");
	} // constructor

	/**
	 * Get the appointment date
	 * 
	 * @return Appointment date
	 */
	public String getApptDate()
	{
		return this.apptDate;
	} // getApptDate

	/**
	 * Get the appointment status
	 * 
	 * @return Appointment status
	 */
	public String getApptStatus()
	{
		return this.apptStatus;
	} // getApptStatus

	/**
	 * Get the appointment time
	 * 
	 * @return Appointment time
	 */
	public String getApptTime()
	{
		return this.apptTime;
	} // getApptTime

	/**
	 * Get the bill-to account code/number
	 * 
	 * @return Bill-to account
	 */
	public String getBillTo()
	{
		return this.billTo;
	} // getBillTo

	/**
	 * Get the bill of lading number
	 * 
	 * @return BOL number
	 */
	public String getBol()
	{
		return this.bol;
	} // getBol

	/**
	 * Get the shipment charges
	 * 
	 * @return Shipment charges
	 */
	public String getCharges()
	{
		return this.charges;
	} // getCharges

	/**
	 * Get the consginee account code/number
	 * 
	 * @return Consginee account
	 */
	public String getCons()
	{
		return this.cons;
	} // getCons

	/**
	 * Get the consginee address
	 * 
	 * @return Consginee address
	 */
	public Address getConsAddress()
	{
		return this.consAddress;
	} // getConsAddress

	/**
	 * Get the consignee name
	 * 
	 * @return Consignee name
	 */
	public String getConsName()
	{
		return this.consName;
	} // getConsName

	/**
	 * Get the consginee reference number
	 * 
	 * @return Consginee ref#
	 */
	public String getConsRefnum()
	{
		return this.consRefnum;
	} // getConsRefnum

	/**
	 * Get the control account code/number
	 * 
	 * @return Control account
	 */
	public String getControl()
	{
		return this.control;
	} // getControl

	/**
	 * Get the delivery date disclaimer
	 * 
	 * @return Delivery date disclaimer
	 */
	public String getDelDateDisc()
	{
		return this.delDateDisc;
	} // getDelDateDisc

	/**
	 * Get the delivery date
	 * 
	 * @return Delivery date
	 */
	public String getDeliveryDate()
	{
		return this.deliveryDate;
	} // getDeliveryDate

	/**
	 * Get the delivery time
	 * 
	 * @return Delivery time
	 */
	public String getDeliveryTime()
	{
		return this.deliveryTime;
	} // getDeliveryTime

	/**
	 * Get the delivery time disclaimer
	 * 
	 * @return Delivery time disclaimer
	 */
	public String getDelTimeDisc()
	{
		return this.delTimeDisc;
	} // getDelTimeDisc

	/**
	 * Get the destination terminal address
	 * 
	 * @return Destination terminal address
	 */
	public Address getDestTerminalAddress()
	{
		return this.destTerminalAddress;
	} // getDestTerminalAddress

	/**
	 * Get the destination terminal fax number
	 * 
	 * @return Destination terminal fax
	 */
	public String getDestTerminalFax()
	{
		return this.destTerminalFax;
	} // getDestTerminalFax
	/**
	 * Get the destination terminal name
	 * 
	 * @return Destination terminal name
	 */
	public String getDestTerminalName()
	{
		return this.destTerminalName;
	} // getDestTerminalName

	/**
	 * Get the destination terminal number
	 * 
	 * @return Destination terminal number
	 */
	public String getDestTerminalNum()
	{
		return this.destTerminalNum;
	} // getDestTerminalNum

	/**
	 * Get the dimension weight
	 * 
	 * @return Dimension weight
	 */
	public String getDimWeight()
	{
		return this.dimWeight;
	} // getDimWeight

	/**
	 * Get the destination terminal phone number
	 * 
	 * @return Destination terminal phone
	 */
	public String getDestTerminalPhone()
	{
		return this.destTerminalPhone;
	} // getDestTerminalPhone

	/**
	 * Get the error message
	 * 
	 * @return Error message
	 */
	public String getError()
	{
		return this.error;
	} // getError

	/**
	 * Get the service response
	 * 
	 * @return Authentication {@link ServiceResponse}
	 */
	public ServiceResponse getErrorInfo()
	{
		return this.errorInfo;
	} // getErrorInfo

	/**
	 * Get the estimated arrival time
	 * 
	 * @return Estimated arrival time
	 */
	public String getEstArrivalTime()
	{
		return this.estArrivalTime;
	} // getEstArrivalTime

	/**
	 * Get the estimated delivery date
	 * 
	 * @return Estimated delivery date
	 */
	public String getEstDelDate()
	{
		return this.estDelDate;
	} // getEstDelDate

	/**
	 * Get the estimated delivery date disclaimer
	 * 
	 * @return Estimated delivery date disclaimer
	 */
	public String getEstDelDateDisc()
	{
		return this.estDelDateDisc;
	} // getEstDelDateDisc

	/**
	 * Get the 1st delivery attempt date
	 * 
	 * @return 1st delivery attempt
	 */
	public String getFirstDeliveryAttempt()
	{
		return this.firstDeliveryAttempt;
	} // getFirstDeliveryAttempt

	/**
	 * Get the freight charge audit
	 * 
	 * @return Freight charge audit
	 */
	public String getFreightChargeAudit()
	{
		return this.freightChargeAudit;
	} // getFreightChargeAudit

	/**
	 * Get the freight charge disclaimer
	 * 
	 * @return Freight charge disclaimer
	 */
	public String getFreightChargeDisc()
	{
		return this.freightChargeDisc;
	} // getFreightChargeDisc

	/**
	 * Get the interline freight bill number
	 * 
	 * @return IL freight bill number
	 */
	public String getIlfb()
	{
		return this.ilfb;
	} // getIlfb

	/**
	 * Get the interline name
	 * 
	 * @return IL name
	 */
	public String getIlName()
	{
		return this.ilName;
	} // getIlName

	/**
	 * Get the interline SCAC
	 * 
	 * @return IL SCAC
	 */
	public String getIlScac()
	{
		return this.ilScac;
	} // getIlScac

	/**
	 * Get the interline type
	 * 
	 * @return IL type
	 */
	public String getIlType()
	{
		return this.ilType;
	} // getIlType

	/**
	 * Get the Y/N flag for party to shipment
	 * 
	 * @return Y/N party to shipment
	 */
	public String getIsParty()
	{
		return this.isParty;
	} // getIsParty

	/**
	 * Get the Y/N flag for payor
	 * 
	 * @return Y/N payor
	 */
	public String getIsPayor()
	{
		return this.isPayor;
	} // getIsPayor

	/**
	 * Get the last changed timestamp
	 * 
	 * @return Last changed time
	 */
	public String getLastChanged()
	{
		return this.lastChanged;
	} // getLastChanged

	/**
	 * Get the load number
	 * 
	 * @return Load number
	 */
	public String getLoadNum()
	{
		return this.loadNum;
	} // getLoadNum

	/**
	 * Get the master origin terminal
	 * 
	 * @return Master OT
	 */
	public String getMasterOT()
	{
		return this.masterOT;
	} // getMasterOT

	/**
	 * Get the master origin PRO number
	 * 
	 * @return Master PRO#
	 */
	public String getMasterProNum()
	{
		return this.masterProNum;
	} // getMasterProNum

	/**
	 * Get the payor account code/number
	 * 
	 * @return Payor account
	 */
	public String getPayor()
	{
		return this.payor;
	} // getPayor

	/**
	 * Get the pickup date
	 * 
	 * @return Pickup date
	 */
	public String getPickupDate()
	{
		return this.pickupDate;
	} // getPickupDate

	/**
	 * Get the pickup time
	 * 
	 * @return Pickup time
	 */
	public String getPickupTime()
	{
		return this.pickupTime;
	} // getPickupTime

	/**
	 * Get the # of pieces
	 * 
	 * @return Pieces
	 */
	public String getPieces()
	{
		return this.pieces;
	} // getPieces

	/**
	 * Get the purchase order number
	 * 
	 * @return PO number
	 */
	public String getPoNum()
	{
		return this.poNum;
	} // getPoNum

	/**
	 * Get the PRO
	 * 
	 * @return Estes PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

	/**
	 * Get the receiving info
	 * 
	 * @return Receiving info
	 */
	public String getReceivedBy()
	{
		return this.receivedBy;
	} // getReceivedBy

	/**
	 * Get the service type code
	 * 
	 * @return Service type
	 */
	public String getServiceType()
	{
		return this.serviceType;
	} // getServiceType

	/**
	 * Get the shipper account code/number
	 * 
	 * @return Shipper account
	 */
	public String getShipper()
	{
		return this.shipper;
	} // getShipper

	/**
	 * Get the shipper address
	 * 
	 * @return Shipper address
	 */
	public Address getShipperAddress()
	{
		return this.shipperAddress;
	} // getShipperAddress

	/**
	 * Get the shipper name
	 * 
	 * @return Shipper name
	 */
	public String getShipperName()
	{
		return this.shipperName;
	} // getShipperName

	/**
	 * Get the shipper reference number
	 * 
	 * @return Shipper ref#
	 */
	public String getShipperRefnum()
	{
		return this.shipperRefnum;
	} // getShipperRefnum

	/**
	 * Get the compare string for sorting
	 * 
	 * @return Sort compare string
	 */
	public String getSortCompare()
	{
		return this.sortCompare;
	} // getSortCompare

	/**
	 * Get the status text
	 * 
	 * @return Status
	 */
	public String getStatus()
	{
		return this.status;
	} // getStatus

	/**
	 * Get the status timestamp
	 * 
	 * @return Status time
	 */
	public String getStatusTime()
	{
		return this.statusTime;
	} // getStatusTime

	/**
	 * Get the terms code
	 * 
	 * @return Terms code
	 */
	public String getTerms()
	{
		return this.terms;
	} // getTerms

	/**
	 * Get the third party address
	 * 
	 * @return Third party address
	 */
	public Address getThirdPartyAddress()
	{
		return this.thirdPartyAddress;
	} // getThirdPartyAddress

	/**
	 * Get the third party name
	 * 
	 * @return Third party name
	 */
	public String getThirdPartyName()
	{
		return this.thirdPartyName;
	} // getThirdPartyName

	/**
	 * Get the third party reference number
	 * 
	 * @return Third party ref#
	 */
	public String getThirdPartyRefnum()
	{
		return this.thirdPartyRefnum;
	} // getThirdPartyRefnum

	/**
	 * Get the shipment type
	 * 
	 * @return Shipment type
	 */
	public String getType()
	{
		return this.type;
	} // getType

	/**
	 * Get the shipment weight
	 * 
	 * @return Weight
	 */
	public String getWeight()
	{
		return this.weight;
	} // getWeight

	/**
	 * Set the appointment date
	 * 
	 * @param val Appointment date
	 */
	public void setApptDate(String val)
	{
		this.apptDate = val.trim();
	} // setApptDate

	/**
	 * Set the appointment status
	 * 
	 * @param val Appointment status
	 */
	public void setApptStatus(String val)
	{
		this.apptStatus = val.trim();
	} // setApptStatus

	/**
	 * Set the appointment time
	 * 
	 * @param val Appointment time
	 */
	public void setApptTime(String val)
	{
		this.apptTime = val.trim();
	} // setApptTime

	/**
	 * Set the bill-to account code/number
	 * 
	 * @param val Bill-to account
	 */
	public void setBillTo(String val)
	{
		this.billTo = val.trim();
	} // setBillTo

	/**
	 * Set the bill of lading number
	 * 
	 * @param val BOL number
	 */
	public void setBol(String val)
	{
		this.bol = val.trim();
	} // setBol

	/**
	 * Set the shipment charges
	 * 
	 * @param val Charges
	 */
	public void setCharges(String val)
	{
		this.charges = val.trim();
	} // setCharges

	/**
	 * Set the consignee account code/number
	 * 
	 * @param val Consignee account
	 */
	public void setCons(String val)
	{
		this.cons = val.trim();
	} // setCons

	/**
	 * Set the consginee address
	 * 
	 * @param val Consginee address
	 */
	public void setConsAddress(Address val)
	{
		this.consAddress = val;
	} // setConsAddress

	/**
	 * Set the consginee name
	 * 
	 * @param val Consginee name
	 */
	public void setConsName(String val)
	{
		this.consName = val.trim();
	} // setConsName

	/**
	 * Set the consginee reference number
	 * 
	 * @param val Consginee ref#
	 */
	public void setConsRefnum(String val)
	{
		this.consRefnum = val.trim();
	} // setConsRefnum

	/**
	 * Set the control account code/number
	 * 
	 * @param val Control account
	 */
	public void setControl(String val)
	{
		this.control = val.trim();
	} // setControl

	/**
	 * Set the delivery time disclaimer
	 * 
	 * @param val Delivery time disclaimer
	 */
	public void setDelDateDisc(String val)
	{
		this.delDateDisc = val.trim();
	} // setDelDateDisc

	/**
	 * Set the delivery date
	 * 
	 * @param val Delivery date
	 */
	public void setDeliveryDate(String val)
	{
		this.deliveryDate = val.trim();
	} // setDeliveryDate

	/**
	 * Set the delivery time
	 * 
	 * @param val Delivery time
	 */
	public void setDeliveryTime(String val)
	{
		this.deliveryTime = val.trim();
	} // setDeliveryTime

	/**
	 * Set the delivery time disclaimer
	 * 
	 * @param val Delivery time disclaimer
	 */
	public void setDelTimeDisc(String val)
	{
		this.delTimeDisc = val.trim();
	} // setDelTimeDisc

	/**
	 * Set the destination terminal address
	 * 
	 * @param val Destination terminal address
	 */
	public void setDestTerminalAddress(Address val)
	{
		this.destTerminalAddress = val;
	} // setDestTerminalAddress

	/**
	 * Set the destination terminal fax number
	 * 
	 * @param val Destination terminal fax
	 */
	public void setDestTerminalFax(String val)
	{
		this.destTerminalFax = val.trim();
	} // setDestTerminalFax

	/**
	 * Set the destination terminal name
	 * 
	 * @param val Destination terminal name
	 */
	public void setDestTerminalName(String val)
	{
		this.destTerminalName = val.trim();
	} // setDestTerminalName

	/**
	 * Set the destination terminal number
	 * 
	 * @param val Destination terminal #
	 */
	public void setDestTerminalNum(String val)
	{
		this.destTerminalNum = val.trim();
	} // setDestTerminalNum

	/**
	 * Set the destination terminal phone number
	 * 
	 * @param val Destination terminal phone
	 */
	public void setDestTerminalPhone(String val)
	{
		this.destTerminalPhone = val.trim();
	} // setDestTerminalPhone

	/**
	 * Set the dimension weight
	 * 
	 * @param val Dimension weight
	 */
	public void setDimWeight(String val)
	{
		this.dimWeight = val.trim();
	} // setDimWeight

	/**
	 * Set the error message
	 * 
	 * @param val Error message
	 */
	public void setError(String val)
	{
		this.error = val.trim();
	} // setError

	/**
	 * Set the service response
	 * 
	 * @param val Service response
	 */
	public void setErrorInfo(ServiceResponse val)
	{
		this.errorInfo = val;
	} // setErrorInfo

	/**
	 * Set the estimated arrival time
	 * 
	 * @param val Estimated arrival time
	 */
	public void setEstArrivalTime(String val)
	{
		this.estArrivalTime = val.trim();
	} // setEstArrivalTime

	/**
	 * Set the estimated delivery date
	 * 
	 * @param val Estimated delivery date
	 */
	public void setEstDelDate(String val)
	{
		this.estDelDate = val.trim();
	} // setEstDelDate

	/**
	 * Set the estimated delivery date disclaimer
	 * 
	 * @param val Estimated delivery date disclaimer
	 */
	public void setEstDelDateDisc(String val)
	{
		this.estDelDateDisc = val.trim();
	} // setEstDelDateDisc

	/**
	 * Set the 1st delivery attempt date
	 * 
	 * @param val 1st delivery attempt
	 */
	public void setFirstDeliveryAttempt(String val)
	{
		this.firstDeliveryAttempt= val.trim();
	} // setFirstDeliveryAttempt

	/**
	 * Set the freight charge audit text
	 * 
	 * @param val Freight charge audit
	 */
	public void setFreightChargeAudit(String val)
	{
		this.freightChargeAudit = val.trim();
	} // setFreightChargeAudit

	/**
	 * Set the freight charge disclaimer
	 * 
	 * @param val Freight charge disclaimer
	 */
	public void setFreightChargeDisc(String val)
	{
		this.freightChargeDisc = val.trim();
	} // setFreightChargeDisc

	/**
	 * Set the interline freight bill number
	 * 
	 * @param val IL freight bill number
	 */
	public void setIlfb(String val)
	{
		this.ilfb = val.trim();
	} // setIlfb

	/**
	 * Set the interline name
	 * 
	 * @param val IL name
	 */
	public void setIlName(String val)
	{
		this.ilName = val.trim();
	} // setIlName

	/**
	 * Set the interline SCAC
	 * 
	 * @param val IL SCAC
	 */
	public void setIlScac(String val)
	{
		this.ilScac = val.trim();
	} // setIlScac

	/**
	 * Set the interline type
	 * 
	 * @param val IL type
	 */
	public void setIlType(String val)
	{
		this.ilType = val.trim();
	} // setIlType

	/**
	 * Get the Y/N flag for party to shipment
	 * 
	 * @return Y/N party to shipment
	 */
	public void setIsPayor(String isPayor)
	{
		this.isPayor = isPayor;
	} // setIsPayor

	/**
	 * Get the Y/N flag for payor
	 * 
	 * @return Y/N payor
	 */
	public void setIsParty(String isParty)
	{
		this.isParty = isParty;
	} // setIsParty

	/**
	 * Set the last changed timestamp
	 * 
	 * @param val Last changed time
	 */
	public void setLastChanged(String lastChanged)
	{
		this.lastChanged = lastChanged;
	} // setLastChanged

	/**
	 * Set the load number
	 * 
	 * @param val Load number
	 */
	public void setLoadNum(String val)
	{
		this.loadNum = val.trim();
	} // setLoadNum

	/**
	 * Set the master original terminal
	 * 
	 * @param val master OT
	 */
	public void setMasterOT(String val)
	{
		this.masterOT = val.trim();
	} // setMasterOT

	/**
	 * Set the master PRO number
	 * 
	 * @param val Master PRO#
	 */
	public void setMasterProNum(String val)
	{
		this.masterProNum = val.trim();
	} // setMasterProNum

	/**
	 * Set the payor account code/number
	 * 
	 * @param val Payor account
	 */
	public void setPayor(String val)
	{
		this.payor = val.trim();
	} // setPayor

	/**
	 * Set the pickup date
	 * 
	 * @param val Pickup date
	 */
	public void setPickupDate(String val)
	{
		this.pickupDate = val.trim();
	} // setPickupDate

	/**
	 * Set the pickup time
	 * 
	 * @param val Pickup time
	 */
	public void setPickupTime(String val)
	{
		this.pickupTime = val.trim();
	} // setPickupTime

	/**
	 * Set the # of pieces
	 * 
	 * @param val Pieces
	 */
	public void setPieces(String val)
	{
		this.pieces = val.trim();
	} // setPieces

	/**
	 * Set the purchase order number
	 * 
	 * @param val PO number
	 */
	public void setPoNum(String val)
	{
		this.poNum = val.trim();
	} // setPoNum

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val.trim();
	} // setPro

	/**
	 * Set the receiving info
	 * 
	 * @param val Receiving info
	 */
	public void setReceivedBy(String val)
	{
		this.receivedBy = val.trim();
	} // setReceivedBy

	/**
	 * Set the service type code
	 * 
	 * @param val Service type
	 */
	public void setServiceType(String val)
	{
		this.serviceType = val;
	} // setServiceType

	/**
	 * Set the shipper account code/number
	 * 
	 * @param val Shipper account
	 */
	public void setShipper(String val)
	{
		this.shipper = val.trim();
	} // setShipper

	/**
	 * Set the shipper address
	 * 
	 * @param val Shipper name
	 */
	public void setShipperAddress(Address val)
	{
		this.shipperAddress = val;
	} // setShipperAddress

	/**
	 * Get the shipper reference number
	 * 
	 * @param val Shipper ref#
	 */
	public void setShipperRefnum(String val)
	{
		this.shipperRefnum = val.trim();
	} // setShippesetShipperRefnumrName

	/**
	 * Set the shipper name
	 * 
	 * @param val Shipper name
	 */
	public void setShipperName(String val)
	{
		this.shipperName = val.trim();
	} // setShipperName

	/**
	 * Set the compare string for sorting
	 * 
	 * @param val Sort compare string
	 */
	public void setSortCompare(String val)
	{
		this.sortCompare = val.trim();
	} // setSortCompare

	/**
	 * Set the status text
	 * 
	 * @param val Status
	 */
	public void setStatus(String val)
	{
		this.status = val.trim();
	} // setStatus

	/**
	 * Set the status timestamp
	 * 
	 * @param val Status time
	 */
	public void setStatusTime(String val)
	{
		this.statusTime = val;
	} // setStatusTime

	/**
	 * Set the terms code
	 * 
	 * @param val Terms code
	 */
	public void setTerms(String val)
	{
		this.terms = val.trim();
	} // setTerms

	/**
	 * Set the third party address
	 * 
	 * @param val Third party address
	 */
	public void setThirdPartyAddress(Address val)
	{
		this.thirdPartyAddress = val;
	} // setThirdPartyAddress

	/**
	 * Set the third party name
	 * 
	 * @param val Third party name
	 */
	public void setThirdPartyName(String val)
	{
		this.thirdPartyName = val.trim();
	} // setThirdPartyName

	/**
	 * Set the third party reference number
	 * 
	 * @param val Third party ref#
	 */
	public void setThirdPartyRefnum(String val)
	{
		this.thirdPartyRefnum = val.trim();
	} // setThirdPartyRefnum

	/**
	 * Set the shipment type
	 * 
	 * @param val Shipment type
	 */
	public void setType(String val)
	{
		this.type = val.trim();
	} // setType

	/**
	 * Set the shipment weight
	 * 
	 * @param val Weight
	 */
	public void setWeight(String val)
	{
		this.weight = val.trim();
	} // setWeight

	@Override
	public String toString() {
		return "Shipment [errorInfo=" + errorInfo + ", type=" + type + ", pro=" + pro + ", error=" + error + ", billTo="
				+ billTo + ", shipper=" + shipper + ", cons=" + cons + ", control=" + control + ", payor=" + payor
				+ ", terms=" + terms + ", charges=" + charges + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", receivedBy=" + receivedBy + ", pickupDate=" + pickupDate + ", pickupTime="
				+ pickupTime + ", weight=" + weight + ", pieces=" + pieces + ", shipperName=" + shipperName
				+ ", shipperAddress=" + shipperAddress + ", shipperRefnum=" + shipperRefnum + ", consName=" + consName
				+ ", consAddress=" + consAddress + ", consRefnum=" + consRefnum + ", thirdPartyName=" + thirdPartyName
				+ ", thirdPartyAddress=" + thirdPartyAddress + ", thirdPartyRefnum=" + thirdPartyRefnum
				+ ", serviceType=" + serviceType + ", apptDate=" + apptDate + ", apptTime=" + apptTime + ", apptStatus="
				+ apptStatus + ", destTerminalNum=" + destTerminalNum + ", destTerminalName=" + destTerminalName
				+ ", destTerminalAddress=" + destTerminalAddress + ", destTerminalPhone=" + destTerminalPhone
				+ ", destTerminalFax=" + destTerminalFax + ", bol=" + bol + ", poNum=" + poNum + ", loadNum=" + loadNum
				+ ", ilfb=" + ilfb + ", ilScac=" + ilScac + ", ilName=" + ilName + ", ilType=" + ilType
				+ ", firstDeliveryAttempt=" + firstDeliveryAttempt + ", status=" + status + ", statusTime=" + statusTime
				+ ", estDelDate=" + estDelDate + ", estArrivalTime=" + estArrivalTime + ", dimWeight=" + dimWeight
				+ ", masterOT=" + masterOT + ", masterProNum=" + masterProNum + ", freightChargeDisc="
				+ freightChargeDisc + ", estDelDateDisc=" + estDelDateDisc + ", delDateDisc=" + delDateDisc
				+ ", delTimeDisc=" + delTimeDisc + ", freightChargeAudit=" + freightChargeAudit + ", lastChanged="
				+ lastChanged + ", isPayor=" + isPayor + ", isParty=" + isParty + ", sortCompare=" + sortCompare + "]";
	}
	
	
}
