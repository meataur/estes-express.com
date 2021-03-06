package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.BolHead;

public class BolHeadMapper  implements  RowMapper<BolHead>{

	@Override
	public BolHead mapRow(ResultSet rs, int rowNum) throws SQLException {
		String bolSequence = rs.getString("BOL_SEQUENCE").trim();
		String userName = rs.getString("USER_NAME").trim();
		String accountNumber = rs.getString("ACCOUNT_NUMBER").trim();
		String bolDate = rs.getString("BOL_DATE").trim();
		String pickUp = rs.getString("PICK_UP").trim();
		String pickUpDate = rs.getString("PICK_UP_DATE").trim();
		String bolNumber = rs.getString("BOL_NUMBER").trim();
		String proOrigTerm = rs.getString("PR_OT").trim();
		String proNumber = rs.getString("PR_NM").trim();
		String autoAsignPro = rs.getString("AUTO_ASIGN_PRO").trim();
		String pickupReqUser = rs.getString("PICKUP_REQ_USER").trim();
		String userFirstName = rs.getString("USER_FIRST_NAME").trim();
		String userLastName = rs.getString("USER_LAST_NAME").trim();
		String userAreaCode = rs.getString("USER_AREA_CODE").trim();
		String userExchange = rs.getString("USER_EXCHANGE").trim();
		String userLast4 = rs.getString("USER_LAST_4").trim();
		String userExt = rs.getString("USER_EXT").trim();
		String userEmail = rs.getString("USER_EMAIL").trim();
		String shipCompany = rs.getString("SHIP_COMPANY").trim();
		String shipAccount = rs.getString("SHIP_ACCOUNT").trim();
		String shipFirstName = rs.getString("SHIP_FIRST_NAME").trim();
		String shipLastName = rs.getString("SHIP_LAST_NAME").trim();
		String shipLocation = rs.getString("SHIP_LOCATION").trim();
		String shipAreaCode = rs.getString("SHIP_AREA_CODE").trim();
		String shipExchange = rs.getString("SHIP_EXCHANGE").trim();
		String shipLast4 = rs.getString("SHIP_LAST_4").trim();
		String shipExt = rs.getString("SHIP_EXT").trim();
		String shipFaxAreaCode = rs.getString("SHIP_FAX_AREA_CODE").trim();
		String shipFaxExchange = rs.getString("SHIP_FAX_EXCHANGE").trim();
		String shipFaxLast4 = rs.getString("SHIP_FAX_LAST_4").trim();
		String shipCountry = rs.getString("SHIP_COUNTRY").trim();
		String shipStr1Address = rs.getString("SHIP_STR1_ADDRESS").trim();
		String shipStr2Address = rs.getString("SHIP_STR2_ADDRESS").trim();
		String shipCity = rs.getString("SHIP_CITY").trim();
		String shipState = rs.getString("SHIP_STATE").trim();
		String shipZip = rs.getString("SHIP_ZIP").trim();
		String shipZip4 = rs.getString("SHIP_ZIP_4").trim();
		String consCompanyName = rs.getString("CONS_COMPANY_NAME").trim();
		String consAccount = rs.getString("CONS_ACCOUNT").trim();
		String consFirstName = rs.getString("CONS_FIRST_NAME").trim();
		String consLastName = rs.getString("CONS_LAST_NAME").trim();
		String consLocationNumber = rs.getString("CONS_LOCATION_NUMBER").trim();
		String consPnAreaCode = rs.getString("CONS_PN_AREA_CODE").trim();
		String consPnExchange = rs.getString("CONS_PN_EXCHANGE").trim();
		String consPnLast4 = rs.getString("CONS_PN_LAST_4").trim();
		String consPnExtension = rs.getString("CONS_PN_EXTENSION").trim();
		String consFnAreaCode = rs.getString("CONS_FN_AREA_CODE").trim();
		String consFnExchange = rs.getString("CONS_FN_EXCHANGE").trim();
		String consFnLast4 = rs.getString("CONS_FN_LAST_4").trim();
		String consCountry = rs.getString("CONS_COUNTRY").trim();
		String consStAddr1 = rs.getString("CONS_ST_ADDR_1").trim();
		String consStAddr2 = rs.getString("CONS_ST_ADDR_2").trim();
		String conscity = rs.getString("CONSCITY").trim();
		String consstate = rs.getString("CONSSTATE").trim();
		String conszip = rs.getString("CONSZIP").trim();
		String consZip4 = rs.getString("CONS_ZIP_4").trim();
		String tptyCompanyName = rs.getString("TPTY_COMPANY_NAME").trim();
		String tptyAccount = rs.getString("TPTY_ACCOUNT").trim();
		String tptyFirstName = rs.getString("TPTY_FIRST_NAME").trim();
		String tptyLastName = rs.getString("TPTY_LAST_NAME").trim();
		String tptyLocationNumber = rs.getString("TPTY_LOCATION_NUMBER").trim();
		String tptyPnAreaCode = rs.getString("TPTY_PN_AREA_CODE").trim();
		String tptyPnExchange = rs.getString("TPTY_PN_EXCHANGE").trim();
		String tptyPnLast4 = rs.getString("TPTY_PN_LAST_4").trim();
		String tptyPnExtension = rs.getString("TPTY_PN_EXTENSION").trim();
		String tptyFnAreaCode = rs.getString("TPTY_FN_AREA_CODE").trim();
		String tptyFnExchange = rs.getString("TPTY_FN_EXCHANGE").trim();
		String tptyFnLast4 = rs.getString("TPTY_FN_LAST_4").trim();
		String tptyCountry = rs.getString("TPTY_COUNTRY").trim();
		String tptyStAddr1 = rs.getString("TPTY_ST_ADDR_1").trim();
		String tptyStAddr2 = rs.getString("TPTY_ST_ADDR_2").trim();
		String tptycity = rs.getString("TPTYCITY").trim();
		String tptystate = rs.getString("TPTYSTATE").trim();
		String tptyzip = rs.getString("TPTYZIP").trim();
		String tptyZip4 = rs.getString("TPTY_ZIP_4").trim();
		String payor = rs.getString("PAYOR").trim();
		String terms = rs.getString("TERMS").trim();
		String codFlg = rs.getString("COD_FLG").trim();
		String codAmount = rs.getString("COD_AMOUNT").trim();
		String codPaymentType = rs.getString("COD_PAYMENT_TYPE").trim();
		String codFeePaidby = rs.getString("COD_FEE_PAIDBY").trim();
		String codname = rs.getString("CODNAME").trim();
		String codFirstName = rs.getString("COD_FIRST_NAME").trim();
		String codLastName = rs.getString("COD_LAST_NAME").trim();
		String codLocationNumber = rs.getString("COD_LOCATION_NUMBER").trim();
		String codPnAreaCode = rs.getString("COD_PN_AREA_CODE").trim();
		String codPnExchange = rs.getString("COD_PN_EXCHANGE").trim();
		String codPnLast4 = rs.getString("COD_PN_LAST_4").trim();
		String codPnExtension = rs.getString("COD_PN_EXTENSION").trim();
		String codCountry = rs.getString("COD_COUNTRY").trim();
		String codStAddr1 = rs.getString("COD_ST_ADDR_1").trim();
		String codStAddr2 = rs.getString("COD_ST_ADDR_2").trim();
		String codcity = rs.getString("CODCITY").trim();
		String codstate = rs.getString("CODSTATE").trim();
		String codzip = rs.getString("CODZIP").trim();
		String codZip4 = rs.getString("COD_ZIP_4").trim();
		String totalPackages = rs.getString("TOTAL_PACKAGES").trim();
		String totalShippingUnits = rs.getString("TOTAL_SHIPPING_UNITS").trim();
		String totalWeight = rs.getString("TOTAL_WEIGHT").trim();
		String totalValue = rs.getString("TOTAL_VALUE").trim();
		String goldStar = rs.getString("GOLD_STAR").trim();
		String goldStarPickup = rs.getString("GOLD_STAR_PICKUP").trim();
		String goldStarQuote = rs.getString("GOLD_STAR_QUOTE").trim();
		String goldService = rs.getString("GOLD_SERVICE").trim();
		String volumeShipment = rs.getString("VOLUME_SHIPMENT").trim();
		String volumeQuote = rs.getString("VOLUME_QUOTE").trim();
		String shipperEmail = rs.getString("SHIPPER_EMAIL").trim();
		String consigneeEmail = rs.getString("CONSIGNEE_EMAIL").trim();
		String thirdpartyEmail = rs.getString("THIRDPARTY_EMAIL").trim();
		String codEmail = rs.getString("COD_EMAIL").trim();
		String emailBolShipper = rs.getString("EMAIL_BOL_SHIPPER").trim();
		String emailBolCons = rs.getString("EMAIL_BOL_CONS").trim();
		String emailBolTpty = rs.getString("EMAIL_BOL_TPTY").trim();
		String emailTrkShipper = rs.getString("EMAIL_TRK_SHIPPER").trim();
		String emailTrkCons = rs.getString("EMAIL_TRK_CONS").trim();
		String emailTrkTpty = rs.getString("EMAIL_TRK_TPTY").trim();
		String hazmatContact = rs.getString("HAZMAT_CONTACT").trim();
		String hazmatAreaCode = rs.getString("HAZMAT_AREA_CODE").trim();
		String hazmatExchange = rs.getString("HAZMAT_EXCHANGE").trim();
		String hazmatLast4 = rs.getString("HAZMAT_LAST_4").trim();
		String hazmatExtension = rs.getString("HAZMAT_EXTENSION").trim();
		String pickupInstructions = rs.getString("PICKUP_INSTRUCTIONS").trim();
		String specialInstructions = rs.getString("SPECIAL_INSTRUCTIONS").trim();
		String cube = rs.getString("CUBE").trim();
		String updatedByPgm = rs.getString("UPDATED_BY_PGM").trim();
		String complete = rs.getString("COMPLETE").trim();
		String availableTime = rs.getString("AVAILABLE_TIME").trim();
		String availableAmPm = rs.getString("AVAILABLE_AM_PM").trim();
		String closeTime = rs.getString("CLOSE_TIME").trim();
		String closeAmPm = rs.getString("CLOSE_AM_PM").trim();
		String ebolUpdated = rs.getString("EBOL_UPDATED").trim();
		String faxToShipper = rs.getString("FAX_TO_SHIPPER").trim();
		String faxToConsignee = rs.getString("FAX_TO_CONSIGNEE").trim();
		String faxToThirdParty = rs.getString("FAX_TO_THIRD_PARTY").trim();
		String trailerNumber = rs.getString("TRAILER_NUMBER").trim();
		String sealNumber = rs.getString("SEAL_NUMBER").trim();
		String sCAC = rs.getString("S_C_A_C").trim();
		String masterBol = rs.getString("MASTER_BOL").trim();
		String masterBolNum = rs.getString("MASTER_BOL_NUM").trim();
		String bolType = rs.getString("BOL_TYPE").trim();
		String pickupDetails = rs.getString("PICKUP_DETAILS").trim();
		String shipmentDetails = rs.getString("SHIPMENT_DETAILS").trim();
		String notifyPickupReceipt = rs.getString("NOTIFY_PICKUP_RECEIPT").trim();
		String notifyPickupAccept = rs.getString("NOTIFY_PICKUP_ACCEPT").trim();
		String notifyPickupReject = rs.getString("NOTIFY_PICKUP_REJECT").trim();
		String notifyPickupBegin = rs.getString("NOTIFY_PICKUP_BEGIN").trim();
		String notifyPickupComplete = rs.getString("NOTIFY_PICKUP_COMPLETE").trim();
		String startLabel = rs.getString("START_LABEL");
		String totalLabel = rs.getString("TOTAL_LABEL");
		String labelType = rs.getString("LABEL_TYPE");
		String emailLabelsShipper = rs.getString("EMAIL_LABELS_SHIPPER").trim();
		String emailLabelsConsignee = rs.getString("EMAIL_LABELS_CONSIGNEE").trim();
		String emailLabelsThirdparty = rs.getString("EMAIL_LABELS_THIRDPARTY").trim();
		String emailLabelsOther = rs.getString("EMAIL_LABELS_OTHER").trim();
		String emailLabelsOtherAddress = rs.getString("EMAIL_LABELS_OTHER_ADDRESS").trim();
		String faxBolShipper = rs.getString("FAX_BOL_SHIPPER").trim();
		String faxBolConsignee = rs.getString("FAX_BOL_CONSIGNEE").trim();
		String faxBolThirdparty = rs.getString("FAX_BOL_THIRDPARTY").trim();
		String faxBolOther = rs.getString("FAX_BOL_OTHER").trim();
		String faxBolNumber = rs.getString("FAX_BOL_NUMBER").trim();
		String emailTrackingOther = rs.getString("EMAIL_TRACKING_OTHER").trim();

		
		BolHead bolHead = new BolHead();
		bolHead.setBolSequence(bolSequence);
		bolHead.setUserName(userName);
		bolHead.setAccountNumber(accountNumber);
		bolHead.setBolDate(bolDate);
		bolHead.setPickUp(pickUp);
		bolHead.setPickUpDate(pickUpDate);
		bolHead.setBolNumber(bolNumber);
		bolHead.setProOrigTerm(proOrigTerm);
		bolHead.setProNumber(proNumber);
		bolHead.setAutoAsignPro(autoAsignPro);
		bolHead.setPickupReqUser(pickupReqUser);
		bolHead.setUserFirstName(userFirstName);
		bolHead.setUserLastName(userLastName);
		bolHead.setUserAreaCode(userAreaCode);
		bolHead.setUserExchange(userExchange);
		bolHead.setUserLast4(userLast4);
		bolHead.setUserExt(userExt);
		bolHead.setUserEmail(userEmail);
		bolHead.setShipCompany(shipCompany);
		bolHead.setShipAccount(shipAccount);
		bolHead.setShipFirstName(shipFirstName);
		bolHead.setShipLastName(shipLastName);
		bolHead.setShipLocation(shipLocation);
		bolHead.setShipAreaCode(shipAreaCode);
		bolHead.setShipExchange(shipExchange);
		bolHead.setShipLast4(shipLast4);
		bolHead.setShipExt(shipExt);
		bolHead.setShipFaxAreaCode(shipFaxAreaCode);
		bolHead.setShipFaxExchange(shipFaxExchange);
		bolHead.setShipFaxLast4(shipFaxLast4);
		bolHead.setShipCountry(shipCountry);
		bolHead.setShipStr1Address(shipStr1Address);
		bolHead.setShipStr2Address(shipStr2Address);
		bolHead.setShipCity(shipCity);
		bolHead.setShipState(shipState);
		bolHead.setShipZip(shipZip);
		bolHead.setShipZip4(shipZip4);
		bolHead.setConsCompanyName(consCompanyName);
		bolHead.setConsAccount(consAccount);
		bolHead.setConsFirstName(consFirstName);
		bolHead.setConsLastName(consLastName);
		bolHead.setConsLocationNumber(consLocationNumber);
		bolHead.setConsPnAreaCode(consPnAreaCode);
		bolHead.setConsPnExchange(consPnExchange);
		bolHead.setConsPnLast4(consPnLast4);
		bolHead.setConsPnExtension(consPnExtension);
		bolHead.setConsFnAreaCode(consFnAreaCode);
		bolHead.setConsFnExchange(consFnExchange);
		bolHead.setConsFnLast4(consFnLast4);
		bolHead.setConsCountry(consCountry);
		bolHead.setConsStAddr1(consStAddr1);
		bolHead.setConsStAddr2(consStAddr2);
		bolHead.setConscity(conscity);
		bolHead.setConsstate(consstate);
		bolHead.setConszip(conszip);
		bolHead.setConsZip4(consZip4);
		bolHead.setTptyCompanyName(tptyCompanyName);
		bolHead.setTptyAccount(tptyAccount);
		bolHead.setTptyFirstName(tptyFirstName);
		bolHead.setTptyLastName(tptyLastName);
		bolHead.setTptyLocationNumber(tptyLocationNumber);
		bolHead.setTptyPnAreaCode(tptyPnAreaCode);
		bolHead.setTptyPnExchange(tptyPnExchange);
		bolHead.setTptyPnLast4(tptyPnLast4);
		bolHead.setTptyPnExtension(tptyPnExtension);
		bolHead.setTptyFnAreaCode(tptyFnAreaCode);
		bolHead.setTptyFnExchange(tptyFnExchange);
		bolHead.setTptyFnLast4(tptyFnLast4);
		bolHead.setTptyCountry(tptyCountry);
		bolHead.setTptyStAddr1(tptyStAddr1);
		bolHead.setTptyStAddr2(tptyStAddr2);
		bolHead.setTptycity(tptycity);
		bolHead.setTptystate(tptystate);
		bolHead.setTptyzip(tptyzip);
		bolHead.setTptyZip4(tptyZip4);
		bolHead.setPayor(payor);
		bolHead.setTerms(terms);
		bolHead.setCodFlg(codFlg);
		bolHead.setCodAmount(codAmount);
		bolHead.setCodPaymentType(codPaymentType);
		bolHead.setCodFeePaidby(codFeePaidby);
		bolHead.setCodname(codname);
		bolHead.setCodFirstName(codFirstName);
		bolHead.setCodLastName(codLastName);
		bolHead.setCodLocationNumber(codLocationNumber);
		bolHead.setCodPnAreaCode(codPnAreaCode);
		bolHead.setCodPnExchange(codPnExchange);
		bolHead.setCodPnLast4(codPnLast4);
		bolHead.setCodPnExtension(codPnExtension);
		bolHead.setCodCountry(codCountry);
		bolHead.setCodStAddr1(codStAddr1);
		bolHead.setCodStAddr2(codStAddr2);
		bolHead.setCodcity(codcity);
		bolHead.setCodstate(codstate);
		bolHead.setCodzip(codzip);
		bolHead.setCodZip4(codZip4);
		bolHead.setTotalPackages(totalPackages);
		bolHead.setTotalShippingUnits(totalShippingUnits);
		bolHead.setTotalWeight(totalWeight);
		bolHead.setTotalValue(totalValue);
		bolHead.setGoldStar(goldStar);
		bolHead.setGoldStarPickup(goldStarPickup);
		bolHead.setGoldStarQuote(goldStarQuote);
		bolHead.setGoldService(goldService);
		bolHead.setVolumeShipment(volumeShipment);
		bolHead.setVolumeQuote(volumeQuote);
		bolHead.setShipperEmail(shipperEmail);
		bolHead.setConsigneeEmail(consigneeEmail);
		bolHead.setThirdpartyEmail(thirdpartyEmail);
		bolHead.setCodEmail(codEmail);
		bolHead.setEmailBolShipper(emailBolShipper);
		bolHead.setEmailBolCons(emailBolCons);
		bolHead.setEmailBolTpty(emailBolTpty);
		bolHead.setEmailTrkShipper(emailTrkShipper);
		bolHead.setEmailTrkCons(emailTrkCons);
		bolHead.setEmailTrkTpty(emailTrkTpty);
		bolHead.setHazmatContact(hazmatContact);
		bolHead.setHazmatAreaCode(hazmatAreaCode);
		bolHead.setHazmatExchange(hazmatExchange);
		bolHead.setHazmatLast4(hazmatLast4);
		bolHead.setHazmatExtension(hazmatExtension);
		bolHead.setPickupInstructions(pickupInstructions);
		bolHead.setSpecialInstructions(specialInstructions);
		bolHead.setCube(cube);
		bolHead.setUpdatedByPgm(updatedByPgm);
		bolHead.setComplete(complete);
		bolHead.setAvailableTime(availableTime);
		bolHead.setAvailableAmPm(availableAmPm);
		bolHead.setCloseTime(closeTime);
		bolHead.setCloseAmPm(closeAmPm);
		bolHead.setEbolUpdated(ebolUpdated);
		bolHead.setFaxToShipper(faxToShipper);
		bolHead.setFaxToConsignee(faxToConsignee);
		bolHead.setFaxToThirdParty(faxToThirdParty);
		bolHead.setTrailerNumber(trailerNumber);
		bolHead.setSealNumber(sealNumber);
		bolHead.setSCAC(sCAC);
		bolHead.setMasterBol(masterBol);
		bolHead.setMasterBolNum(masterBolNum);
		bolHead.setBolType(bolType);
		bolHead.setPickupDetails(pickupDetails);
		bolHead.setShipmentDetails(shipmentDetails);
		bolHead.setNotifyPickupReceipt(notifyPickupReceipt);
		bolHead.setNotifyPickupAccept(notifyPickupAccept);
		bolHead.setNotifyPickupReject(notifyPickupReject);
		bolHead.setNotifyPickupBegin(notifyPickupBegin);
		bolHead.setNotifyPickupComplete(notifyPickupComplete);
		bolHead.setStartLabel(startLabel);
		bolHead.setTotalLabel(totalLabel);
		bolHead.setLabelType(labelType);
		bolHead.setEmailLabelsShipper(emailLabelsShipper);
		bolHead.setEmailLabelsConsignee(emailLabelsConsignee);
		bolHead.setEmailLabelsThirdparty(emailLabelsThirdparty);
		bolHead.setEmailLabelsOther(emailLabelsOther);
		bolHead.setEmailLabelsOtherAddress(emailLabelsOtherAddress);
		bolHead.setFaxBolShipper(faxBolShipper);
		bolHead.setFaxBolConsignee(faxBolConsignee);
		bolHead.setFaxBolThirdparty(faxBolThirdparty);
		bolHead.setFaxBolOther(faxBolOther);
		bolHead.setFaxBolNumber(faxBolNumber);
		bolHead.setEmailTrackingOther(emailTrackingOther);
		
		//System.out.println(bolHead);
		return bolHead;
	}

}
