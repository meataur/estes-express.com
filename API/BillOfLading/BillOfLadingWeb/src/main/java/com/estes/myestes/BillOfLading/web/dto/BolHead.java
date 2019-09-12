package com.estes.myestes.BillOfLading.web.dto;

import com.estes.myestes.BillOfLading.util.EstesUtil;

import lombok.Data;

@Data
public class BolHead {
	private String bolSequence; /* DECIMAL(15,0), */ 
	private String userName; /* CHAR(10), */ 
	private String accountNumber; /* CHAR(7), */ 
	private String bolDate; /* DECIMAL(8,0), */ 
	private String pickUp; /* CHAR(1), */ 
	private String pickUpDate; /* DECIMAL(8,0), */ 
	private String bolNumber; /* CHAR(25), */ 
	private String proOrigTerm; /* DECIMAL(3,0), */ 
	private String proNumber; /* DECIMAL(7,0), */ 
	private String autoAsignPro; /* CHAR(1), */ 
	private String pickupReqUser; /* CHAR(1), */ 
	private String userFirstName; /* CHAR(25), */ 
	private String userLastName; /* CHAR(25), */ 
	private String userAreaCode; /* DECIMAL(3,0), */ 
	private String userExchange; /* DECIMAL(3,0), */ 
	private String userLast4; /* DECIMAL(4,0), */ 
	private String userExt; /* CHAR(5), */ 
	private String userEmail; /* CHAR(50), */ 
	private String shipCompany; /* CHAR(30), */ 
	private String shipAccount; /* CHAR(7), */ 
	private String shipFirstName; /* CHAR(25), */ 
	private String shipLastName; /* CHAR(25), */ 
	private String shipLocation; /* CHAR(10), */ 
	private String shipAreaCode; /* DECIMAL(3,0), */ 
	private String shipExchange; /* DECIMAL(3,0), */ 
	private String shipLast4; /* DECIMAL(4,0), */ 
	private String shipExt; /* CHAR(5), */ 
	private String shipFaxAreaCode; /* DECIMAL(3,0), */ 
	private String shipFaxExchange; /* DECIMAL(3,0), */ 
	private String shipFaxLast4; /* DECIMAL(4,0), */ 
	private String shipCountry; /* CHAR(2), */ 
	private String shipStr1Address; /* CHAR(30), */ 
	private String shipStr2Address; /* CHAR(30), */ 
	private String shipCity; /* CHAR(20), */ 
	private String shipState; /* CHAR(2), */ 
	private String shipZip; /* CHAR(6), */ 
	private String shipZip4; /* CHAR(4), */ 
	private String consCompanyName; /* CHAR(30), */ 
	private String consAccount; /* CHAR(7), */ 
	private String consFirstName; /* CHAR(25), */ 
	private String consLastName; /* CHAR(25), */ 
	private String consLocationNumber; /* CHAR(10), */ 
	private String consPnAreaCode; /* DECIMAL(3,0), */ 
	private String consPnExchange; /* DECIMAL(3,0), */ 
	private String consPnLast4; /* DECIMAL(4,0), */ 
	private String consPnExtension; /* CHAR(5), */ 
	private String consFnAreaCode; /* DECIMAL(3,0), */ 
	private String consFnExchange; /* DECIMAL(3,0), */ 
	private String consFnLast4; /* DECIMAL(4,0), */ 
	private String consCountry; /* CHAR(2), */ 
	private String consStAddr1; /* CHAR(30), */ 
	private String consStAddr2; /* CHAR(30), */ 
	private String conscity; /* CHAR(20), */ 
	private String consstate; /* CHAR(2), */ 
	private String conszip; /* CHAR(6), */ 
	private String consZip4; /* CHAR(4), */ 
	private String tptyCompanyName; /* CHAR(30), */ 
	private String tptyAccount; /* CHAR(7), */ 
	private String tptyFirstName; /* CHAR(25), */ 
	private String tptyLastName; /* CHAR(25), */ 
	private String tptyLocationNumber; /* CHAR(10), */ 
	private String tptyPnAreaCode; /* DECIMAL(3,0), */ 
	private String tptyPnExchange; /* DECIMAL(3,0), */ 
	private String tptyPnLast4; /* DECIMAL(4,0), */ 
	private String tptyPnExtension; /* CHAR(5), */ 
	private String tptyFnAreaCode; /* DECIMAL(3,0), */ 
	private String tptyFnExchange; /* DECIMAL(3,0), */ 
	private String tptyFnLast4; /* DECIMAL(4,0), */ 
	private String tptyCountry; /* CHAR(2), */ 
	private String tptyStAddr1; /* CHAR(30), */ 
	private String tptyStAddr2; /* CHAR(30), */ 
	private String tptycity; /* CHAR(20), */ 
	private String tptystate; /* CHAR(2), */ 
	private String tptyzip; /* CHAR(6), */ 
	private String tptyZip4; /* CHAR(4), */ 
	private String payor; /* CHAR(1), */ 
	private String terms; /* CHAR(3), */ 
	private String codFlg; /* CHAR(1), */ 
	private String codAmount; /* DECIMAL(9,2), */ 
	private String codPaymentType; /* CHAR(10), */ 
	private String codFeePaidby; /* CHAR(10), */ 
	private String codname; /* CHAR(30), */ 
	private String codFirstName; /* CHAR(25), */ 
	private String codLastName; /* CHAR(25), */ 
	private String codLocationNumber; /* CHAR(10), */ 
	private String codPnAreaCode; /* DECIMAL(3,0), */ 
	private String codPnExchange; /* DECIMAL(3,0), */ 
	private String codPnLast4; /* DECIMAL(4,0), */ 
	private String codPnExtension; /* CHAR(5), */ 
	private String codCountry; /* CHAR(2), */ 
	private String codStAddr1; /* CHAR(30), */ 
	private String codStAddr2; /* CHAR(30), */ 
	private String codcity; /* CHAR(20), */ 
	private String codstate; /* CHAR(2), */ 
	private String codzip; /* CHAR(6), */ 
	private String codZip4; /* CHAR(4), */ 
	private String totalPackages; /* DECIMAL(5,0), */ 
	private String totalShippingUnits; /* DECIMAL(5,0), */ 
	private String totalWeight; /* DECIMAL(7,0), */ 
	private String totalValue; /* DECIMAL(7,2), */ 
	private String goldStar; /* CHAR(1), */ 
	private String goldStarPickup; /* CHAR(10), */ 
	private String goldStarQuote; /* CHAR(7), */ 
	private String goldService; /* CHAR(5), */ 
	private String volumeShipment; /* CHAR(1), */ 
	private String volumeQuote; /* CHAR(7), */ 
	private String shipperEmail; /* CHAR(50), */ 
	private String consigneeEmail; /* CHAR(50), */ 
	private String thirdpartyEmail; /* CHAR(50), */ 
	private String codEmail; /* CHAR(50), */ 
	private String emailBolShipper; /* CHAR(1), */ 
	private String emailBolCons; /* CHAR(1), */ 
	private String emailBolTpty; /* CHAR(1), */ 
	private String emailTrkShipper; /* CHAR(1), */ 
	private String emailTrkCons; /* CHAR(1), */ 
	private String emailTrkTpty; /* CHAR(1), */ 
	private String hazmatContact; /* CHAR(30), */ 
	private String hazmatAreaCode; /* DECIMAL(3,0), */ 
	private String hazmatExchange; /* DECIMAL(3,0), */ 
	private String hazmatLast4; /* DECIMAL(4,0), */ 
	private String hazmatExtension; /* CHAR(6), */ 
	private String pickupInstructions; /* CHAR(256), */ 
	private String specialInstructions; /* CHAR(500), */ 
	private String cube; /* DECIMAL(7,2), */ 
	private String updatedByPgm; /* CHAR(10), */ 
	private String complete; /* CHAR(1), */ 
	private String availableTime; /* DECIMAL(4,0), */ 
	private String availableAmPm; /* CHAR(2), */ 
	private String closeTime; /* DECIMAL(4,0), */ 
	private String closeAmPm; /* CHAR(2), */ 
	private String ebolUpdated; /* CHAR(1), */ 
	
	private String faxToShipper; /* CHAR(1), */ 
	private String faxToConsignee; /* CHAR(1), */ 
	private String faxToThirdParty; /* CHAR(1), */
	
	private String trailerNumber; /* CHAR(15), */ 
	private String sealNumber; /* CHAR(15), */ 
	private String sCAC; /* CHAR(4), */ 
	private String masterBol; /* CHAR(1), */ 
	private String masterBolNum; /* CHAR(25), */ 
	private String bolType; /* CHAR(1), */ 
	private String pickupDetails; /* CHAR(10), */ 
	private String shipmentDetails; /* CHAR(10), */ 
	private String notifyPickupReceipt; /* CHAR(1), */ 
	private String notifyPickupAccept; /* CHAR(1), */ 
	private String notifyPickupReject; /* CHAR(1), */ 
	private String notifyPickupBegin; /* CHAR(1), */ 
	private String notifyPickupComplete; /* CHAR(1), */ 
	private String startLabel; /* DECIMAL(15, */ 
	private String totalLabel; /* DECIMAL(15, */ 
	private String labelType; /* CHAR(1), */ 
	private String emailLabelsShipper; /* CHAR(1), */ 
	private String emailLabelsConsignee; /* CHAR(1), */ 
	private String emailLabelsThirdparty; /* CHAR(1), */ 
	private String emailLabelsOther; /* CHAR(1), */ 
	private String emailLabelsOtherAddress; /* CHAR(255), */ 
	private String faxBolShipper; /* CHAR(1), */ 
	private String faxBolConsignee; /* CHAR(1), */ 
	private String faxBolThirdparty; /* CHAR(1), */ 
	private String faxBolOther; /* CHAR(1), */ 
	private String faxBolNumber; /* CHAR(10), */ 
	private String emailTrackingOther; /* CHAR(1), */ 

	
	public BillOfLading getBillOfLading(){
		
		BillOfLading bol = new BillOfLading();
		
		bol.setDocumentType(DocumentType.valueOf(bolType));
		
		GeneralInformation genInfo = new GeneralInformation();
		genInfo.setBolNumber(bolNumber);

		if(bolDate!=null && bolDate.trim()!="" && bolDate.replaceAll("\\D+", "").length()==8){
			genInfo.setBolDate(EstesUtil.formatDate(bolDate, EstesUtil.DATE_YYYYMMDD));
		}
		
		genInfo.setAssignProNumber(autoAsignPro.equalsIgnoreCase("Y"));
		
		if(proOrigTerm.equals("")==false 
				&& proOrigTerm.equals("000")==false
				&& proNumber.equals("")==false
				&& proNumber.equals("0000000")==false){
			genInfo.setProNumber(proOrigTerm+"-"+proNumber);
		}
		
		genInfo.setMasterBol(masterBol.equalsIgnoreCase("Y"));
		
		genInfo.setMasterBolNumber(masterBolNum);
		
		genInfo.setGuranteed(goldStar.equalsIgnoreCase("Y"));
		
		genInfo.setQuote(goldStarQuote);
		
		bol.setGeneralInfo(genInfo);
		
		AddressInformation shipper = new AddressInformation();
		
		shipper.setCompanyName(shipCompany);
		shipper.setFirstName(shipFirstName);
		shipper.setLastName(shipLastName);
		shipper.setLocation(shipLocation);
		
		if(shipAreaCode.equals("")==false && shipExchange.equals("")==false && shipLast4.equals("")==false){
			String phone = "("+shipAreaCode+") "+shipExchange+"-"+shipLast4;
			shipper.setPhone(phone);
		}
		
		shipper.setPhoneExt(shipExt);
		
		if(shipFaxAreaCode.equals("")==false && shipFaxExchange.equals("")==false && shipFaxLast4.equals("")==false){
			String fax = "("+shipFaxAreaCode+") "+shipFaxExchange+"-"+shipFaxLast4;
			shipper.setFax(fax);
		}
		
		shipper.setCountry(Country.getValueOf(shipCountry));
		
		shipper.setAddress1(shipStr1Address);
		shipper.setAddress2(shipStr2Address);
		shipper.setCity(shipCity);
		shipper.setState(shipState);
		shipper.setZip(shipZip);
		shipper.setEmail(shipperEmail);
		
		bol.setShipperInfo(shipper);
		
		AddressInformation consignee = new AddressInformation();
		consignee.setCompanyName(consCompanyName);
		consignee.setFirstName(consFirstName);
		consignee.setLastName(consLastName);
		consignee.setLocation(consLocationNumber);
		
		if(consPnAreaCode.equals("")==false && consPnExchange.equals("")==false && consPnLast4.equals("")==false){
			String phone = "("+consPnAreaCode+") "+consPnExchange+"-"+consPnLast4;
			consignee.setPhone(phone);
		}
		
		consignee.setPhoneExt(consPnExtension);
		
		if(consFnAreaCode.equals("")==false && consFnExchange.equals("")==false && consFnLast4.equals("")==false){
			String fax = "("+consFnAreaCode+") "+consFnExchange+"-"+consFnLast4;
			consignee.setFax(fax);
		}
		
		consignee.setCountry(Country.getValueOf(consCountry));
		
		consignee.setAddress1(consStAddr1);
		consignee.setAddress2(consStAddr2);
		consignee.setCity(conscity);
		consignee.setState(consstate);
		consignee.setZip(conszip);
		consignee.setEmail(consigneeEmail);
		
		bol.setConsigneeInfo(consignee);
		
		CommodityInformation commodityInfo = new CommodityInformation();
		
		commodityInfo.setContactName(hazmatContact);

		if(hazmatAreaCode.equals("")==false && hazmatExchange.equals("")==false && hazmatLast4.equals("")==false){
			String phone = "("+hazmatAreaCode+") "+hazmatExchange+"-"+hazmatLast4;
			commodityInfo.setPhone(phone);
		}

		commodityInfo.setPhoneExt(hazmatExtension);
		commodityInfo.setSpecialIns(specialInstructions);
		commodityInfo.setTotalCube(cube);
		
		bol.setCommodityInfo(commodityInfo);
		
		BillingInformation billingInfo = new BillingInformation();
		
		BillTo billTo = new BillTo();
		billTo.setPayor(BillToRole.valueOf(payor));
		billTo.setFee(Fee.valueOf(terms));
		
		AddressInformation billingAddressInfo = new AddressInformation();
		
		billingAddressInfo.setCompanyName(tptyCompanyName);
		billingAddressInfo.setFirstName(tptyFirstName);
		billingAddressInfo.setLastName(tptyLastName);
		billingAddressInfo.setLocation(tptyLocationNumber);
		
		if(tptyPnAreaCode.equals("")==false && tptyPnExchange.equals("")==false && tptyPnLast4.equals("")==false){
			String phone = "("+tptyPnAreaCode+") "+tptyPnExchange+"-"+tptyPnLast4;
			billingAddressInfo.setPhone(phone);
		}
		
		billingAddressInfo.setPhoneExt(tptyPnExtension);
		
		if(tptyFnAreaCode.equals("")==false && tptyFnExchange.equals("")==false && tptyFnLast4.equals("")==false){
			String fax = "("+tptyFnAreaCode+") "+tptyFnExchange+"-"+tptyFnLast4;
			billingAddressInfo.setFax(fax);
		}
		
		billingAddressInfo.setCountry(Country.getValueOf(tptyCountry));
		
		billingAddressInfo.setAddress1(tptyStAddr1);
		billingAddressInfo.setAddress2(tptyStAddr2);
		billingAddressInfo.setCity(tptycity);
		billingAddressInfo.setState(tptystate);
		billingAddressInfo.setZip(tptyzip);
		billingAddressInfo.setEmail(thirdpartyEmail);
		
		billTo.setBillingAddressInfo(billingAddressInfo);
		
		billingInfo.setBillTo(billTo);
		
		
		billingInfo.setCodRemitTo(codFlg.equalsIgnoreCase("Y"));
		
		CodRemitTo codRemitToInfo = new CodRemitTo();
		
		codRemitToInfo.setAmount(codAmount);

		codRemitToInfo.setPaymentType(PaymentType.getValueOf(codPaymentType));
		codRemitToInfo.setFee(CodFee.getValueOf(codFeePaidby));
		
		AddressInformation codAddressInfo = new AddressInformation();
		
		codAddressInfo.setCompanyName(codname);
		codAddressInfo.setFirstName(codFirstName);
		codAddressInfo.setLastName(codLastName);
		codAddressInfo.setLocation(codLocationNumber);
		
		if(codPnAreaCode.equals("")==false && codPnExchange.equals("")==false && codPnLast4.equals("")==false){
			String phone = "("+codPnAreaCode+") "+codPnExchange+"-"+codPnLast4;
			codAddressInfo.setPhone(phone);
		}
		
		codAddressInfo.setPhoneExt(codPnExtension);
		
		codAddressInfo.setCountry(Country.getValueOf(codCountry));
		
		codAddressInfo.setAddress1(codStAddr1);
		codAddressInfo.setAddress2(codStAddr2);
		codAddressInfo.setCity(codcity);
		codAddressInfo.setState(codstate);
		codAddressInfo.setZip(codzip);
		codAddressInfo.setEmail(thirdpartyEmail);
		
		codRemitToInfo.setCodAddressInfo(codAddressInfo);
		billingInfo.setCodRemitToInfo(codRemitToInfo);
		
		bol.setBillingInfo(billingInfo);
		
		
		ShippingLabel shippingLabel = new ShippingLabel();
		
		shippingLabel.setLabelType(labelType);
		shippingLabel.setStartLabel(startLabel);
		shippingLabel.setNumberOfLabel(totalLabel);
		bol.setShippingLabel(shippingLabel);
		
		EmailAndFaxNotification emailAndFaxNotification = new EmailAndFaxNotification();
		
		emailAndFaxNotification.setShipperEmail(shipperEmail);
		
		
		
		emailAndFaxNotification.setShipperEmailBolUpdate(emailBolShipper.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setShipperEmailTrackingUpdate(emailTrkShipper.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setShipperEmailShippingLabel(emailLabelsShipper.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setShipperEmailPickupNotice(false);
		emailAndFaxNotification.setShipperFax(bol.getShipperInfo().getFax());
		emailAndFaxNotification.setShipperFaxBolUpdate(emailBolCons.equalsIgnoreCase("Y"));
		
		
		emailAndFaxNotification.setConsigneeEmail(consigneeEmail);
		emailAndFaxNotification.setConsigneeEmailBolUpdate(emailBolCons.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setConsigneeEmailTrackingUpdate(emailTrkCons.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setConsigneeEmailShippingLabel(emailLabelsConsignee.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setConsigneeEmailPickupNotice(false);
		emailAndFaxNotification.setConsigneeFax(bol.getConsigneeInfo().getFax());
		emailAndFaxNotification.setConsigneeFaxBolUpdate(faxBolConsignee.equalsIgnoreCase("Y"));
		
		
		
		emailAndFaxNotification.setThirdPartyEmail(thirdpartyEmail);
		emailAndFaxNotification.setThirdPartyEmailBolUpdate(emailBolTpty.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setThirdPartyEmailTrackingUpdate(emailTrkTpty.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setThirdPartyEmailShippingLabel(emailLabelsThirdparty.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setThirdPartyEmailPickupNotice(false);
		
		if(tptyFnAreaCode.equals("")==false && tptyFnExchange.equals("")==false && tptyFnLast4.equals("")==false){
			String fax = "("+tptyFnAreaCode+") "+tptyFnExchange+"-"+tptyFnLast4;
			emailAndFaxNotification.setThirdPartyFax(fax);
		}
		
		emailAndFaxNotification.setThirdPartyFaxBolUpdate(faxBolThirdparty.equalsIgnoreCase("Y"));
		
		
		emailAndFaxNotification.setOtherEmail(emailLabelsOtherAddress);
		//TODO
		emailAndFaxNotification.setOtherEmailBolUpdate(false);
		emailAndFaxNotification.setOtherEmailTrackingUpdate(emailTrackingOther.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setOtherEmailShippingLabel(emailLabelsOther.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setOtherEmailPickupNotice(false);
		
		if(faxBolNumber!=null && faxBolNumber.trim().length()>=10){
			String fax = "("+faxBolNumber.substring(0, 3)+") "+faxBolNumber.substring(3, 6)+"-"+faxBolNumber.substring(6);
			emailAndFaxNotification.setOtherFax(fax);
		}
		
		emailAndFaxNotification.setOtherFaxBolUpdate(faxBolOther.equalsIgnoreCase("Y"));
		
		
		
		
		bol.setEmailAndFaxNotification(emailAndFaxNotification);
		
		
		return bol;
		
	}
}
