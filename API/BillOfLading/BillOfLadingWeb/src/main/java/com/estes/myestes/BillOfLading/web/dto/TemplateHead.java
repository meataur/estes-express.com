package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TemplateHead {
	private String userName; /* CHAR(10) */ 
	private String calledByPgm; /* CHAR(10) */ 
	private Date bolDate; /* CHAR(10) */ 
	private String pickUp; /* CHAR(1) */ 
	private String pickUpDate; /* CHAR(10) */ 
	private String bolNumber; /* CHAR(35) */ 
	private String proOrigTerm; /* CHAR(3) */ 
	private String proNumber; /* CHAR(7) */ 
	private String autoAsignPro; /* CHAR(1) */ 
	private String pickupReqUser; /* CHAR(1) */ 
	private String userFirstName; /* CHAR(25) */ 
	private String userLastName; /* CHAR(25) */ 
	private String userAreaCode; /* CHAR(3) */ 
	private String userExchange; /* CHAR(3) */ 
	private String userLast4; /* CHAR(4) */ 
	private String userExt; /* CHAR(5) */ 
	private String userEmail; /* CHAR(50) */ 
	private String shipCompany; /* CHAR(30) */ 
	private String shipAccount; /* CHAR(7) */ 
	private String shipFirstName; /* CHAR(25) */ 
	private String shipLastName; /* CHAR(25) */ 
	private String shipLocation; /* CHAR(10) */ 
	private String shipAreaCode; /* CHAR(3) */ 
	private String shipExchange; /* CHAR(3) */ 
	private String shipLast4; /* CHAR(4) */ 
	private String shipExt; /* CHAR(5) */ 
	private String shipFaxAreaCode; /* CHAR(3) */ 
	private String shipFaxExchange; /* CHAR(3) */ 
	private String shipFaxLast4; /* CHAR(4) */ 
	private String shipCountry; /* CHAR(2) */ 
	private String shipStr1Address; /* CHAR(30) */ 
	private String shipStr2Address; /* CHAR(30) */ 
	private String shipCity; /* CHAR(20) */ 
	private String shipState; /* CHAR(2) */ 
	private String shipZip; /* CHAR(6) */ 
	private String shipZip4; /* CHAR(4) */ 
	private String consCompanyName; /* CHAR(30) */ 
	private String consAccount; /* CHAR(7) */ 
	private String consFirstName; /* CHAR(25) */ 
	private String consLastName; /* CHAR(25) */ 
	private String consLocationNumber; /* CHAR(10) */ 
	private String consPnAreaCode; /* CHAR(3) */ 
	private String consPnExchange; /* CHAR(3) */ 
	private String consPnLast4; /* CHAR(4) */ 
	private String consPnExtension; /* CHAR(5) */ 
	private String consFnAreaCode; /* CHAR(3) */ 
	private String consFnExchange; /* CHAR(3) */ 
	private String consFnLast4; /* CHAR(4) */ 
	private String consCountry; /* CHAR(2) */ 
	private String consStAddr1; /* CHAR(30) */ 
	private String consStAddr2; /* CHAR(30) */ 
	private String consCity; /* CHAR(20) */ 
	private String consState; /* CHAR(2) */ 
	private String consZip; /* CHAR(6) */ 
	private String consZip4; /* CHAR(4) */ 
	private String payorCode; /* CHAR(1) */ 
	private String termsCode; /* CHAR(3) */ 
	private String tptyCompanyName; /* CHAR(30) */ 
	private String tptyAccount; /* CHAR(7) */ 
	private String tptyFirstName; /* CHAR(25) */ 
	private String tptyLastName; /* CHAR(25) */ 
	private String tptyLocationNumber; /* CHAR(10) */ 
	private String tptyPnAreaCode; /* CHAR(3) */ 
	private String tptyPnExchange; /* CHAR(3) */ 
	private String tptyPnLast4; /* CHAR(4) */ 
	private String tptyPnExtension; /* CHAR(5) */ 
	private String tptyFnAreaCode; /* CHAR(3) */ 
	private String tptyFnExchange; /* CHAR(3) */ 
	private String tptyFnLast4; /* CHAR(4) */ 
	private String tptyCountry; /* CHAR(2) */ 
	private String tptyStAddr1; /* CHAR(30) */ 
	private String tptyStAddr2; /* CHAR(30) */ 
	private String tptyCity; /* CHAR(20) */ 
	private String tptyState; /* CHAR(2) */ 
	private String tptyZip; /* CHAR(6) */ 
	private String tptyZip4; /* CHAR(4) */ 
	private String codFlg; /* CHAR(1) */ 
	private String codAmount; /* CHAR(11) */ 
	private String codPaymentType; /* CHAR(10) */ 
	private String codFeePaidby; /* CHAR(10) */ 
	private String codname; /* CHAR(30) */ 
	private String codFirstName; /* CHAR(25) */ 
	private String codLastName; /* CHAR(25) */ 
	private String codLocationNumber; /* CHAR(10) */ 
	private String codPnAreaCode; /* CHAR(3) */ 
	private String codPnExchange; /* CHAR(3) */ 
	private String codPnLast4; /* CHAR(4) */ 
	private String codPnExtension; /* CHAR(5) */ 
	private String codCountry; /* CHAR(2) */ 
	private String codStAddr1; /* CHAR(30) */ 
	private String codStAddr2; /* CHAR(30) */ 
	private String codCity; /* CHAR(20) */ 
	private String codState; /* CHAR(2) */ 
	private String codZip; /* CHAR(6) */ 
	private String codZip4; /* CHAR(4) */ 
	private String goldStar; /* CHAR(1) */ 
	private String goldStarPickup; /* CHAR(10) */ 
	private String goldStarQuote; /* CHAR(7) */ 
	private String goldService; /* CHAR(5) */ 
	private String volumeShipment; /* CHAR(1) */ 
	private String volumeQuote; /* CHAR(7) */ 
	private String shipperEmail; /* CHAR(50) */ 
	private String consigneeEmail; /* CHAR(50) */ 
	private String thirdpartyEmail; /* CHAR(50) */ 
	private String codEmail; /* CHAR(50) */ 
	private String emailBolShipper; /* CHAR(1) */ 
	private String emailBolCons; /* CHAR(1) */ 
	private String emailBolTpty; /* CHAR(1) */ 
	private String emailTrkShipper; /* CHAR(1) */ 
	private String emailTrkCons; /* CHAR(1) */ 
	private String emailTrkTpty; /* CHAR(1) */ 
	private String totalPackages; /* CHAR(5) */ 
	private String totalShipUnits; /* CHAR(5) */ 
	private String totalWeight; /* CHAR(7) */ 
	private String totalValue; /* CHAR(9) */ 
	private String hazmatContact; /* CHAR(30) */ 
	private String hazmatAreaCode; /* CHAR(3) */ 
	private String hazmatExchange; /* CHAR(3) */ 
	private String hazmatLast4; /* CHAR(4) */ 
	private String hazmatExtension; /* CHAR(6) */ 
	private String pickupInstructions; /* CHAR(256) */ 
	private String specialInstructions; /* CHAR(500) */ 
	private String cube; /* CHAR(9) */ 
	private String availableTime; /* CHAR(4) */ 
	private String availableAmPm; /* CHAR(2) */ 
	private String closeTime; /* CHAR(4) */ 
	private String closeAmPm; /* CHAR(2) */ 
	private String startLabel; /* CHAR(15) */ 
	private String totalLabel; /* CHAR(15) */ 
	private String faxToShipper; /* CHAR(1) */ 
	private String faxToConsignee; /* CHAR(1) */ 
	private String faxToThirdParty; /* CHAR(1) */ 
	private String trailerNumber; /* CHAR(15) */ 
	private String sealNumber; /* CHAR(15) */ 
	private String scac; /* CHAR(4) */ 
	private String masterBol; /* CHAR(1) */ 
	private String masterBolNum; /* CHAR(25) */ 
	private String bolType; /* CHAR(1) */ 
	private String pickupDetails; /* CHAR(10) */ 
	private String shipmentDetails; /* CHAR(10) */ 
	private String notifyPickupReceipt; /* CHAR(1) */ 
	private String notifyPickupAccept; /* CHAR(1) */ 
	private String notifyPickupReject; /* CHAR(1) */ 
	private String notifyPickupBegin; /* CHAR(1) */ 
	private String notifyPickupComplete; /* CHAR(1) */ 
	private String emailBolOther; /* CHAR(1) */ 
	private String emailTrkOther; /* CHAR(1) */ 
	private String emailLabelShipper; /* CHAR(1) */ 
	private String emailLabelCons; /* CHAR(1) */ 
	private String emailLabelTpty; /* CHAR(1) */ 
	private String emailLabelOther; /* CHAR(1) */ 
	private String faxToOther; /* CHAR(1) */ 
	private String otherFaxAreaCode; /* CHAR(3) */ 
	private String otherFaxExchange; /* CHAR(3) */ 
	private String otherFaxLast4; /* CHAR(4) */ 
	private String labelType; /* CHAR(1) */ 
	private String emailOtherAddr; /* CHAR(255) */ 
	private String faxBolShipper; /* CHAR(1) */ 
	private String faxBolConsignee; /* CHAR(1) */ 
	private String faxBolTpty; /* CHAR(1) */ 
	private String faxBolOther; /* CHAR(1) */ 
	private String faxBolNumber; /* CHAR(10) */ 
	
	public TemplateHead(BillOfLading bol){
		
		this.bolType = bol.getDocumentType()==null? "" : bol.getDocumentType().name();
		
		/**
		 * Set General Information
		 */
		
		if(bol.getGeneralInfo()!=null){
			
			if(bol.getGeneralInfo().getBolDate()!=null){
				this.bolDate = bol.getGeneralInfo().getBolDate();
			}
			
			if(bol.getGeneralInfo().getBolNumber()!=null){
				this.bolNumber = bol.getGeneralInfo().getBolNumber();
			}
			
			if(bol.getGeneralInfo().getProNumber()!=null){
				String[] proNumbers = bol.getGeneralInfo().getProNumber().split("-");
				
				if(proNumbers.length==2){
					this.proOrigTerm = proNumbers[0];
					this.proNumber = proNumbers[1];
				}
			}
			
			if(bol.getGeneralInfo().isAssignProNumber()==true){
				this.autoAsignPro = "Y";
			}
			
			this.masterBol = bol.getGeneralInfo().isMasterBol()==true? "Y" : "N";
			
			this.masterBolNum = bol.getGeneralInfo().getMasterBolNumber();
			
			this.goldStar = bol.getGeneralInfo().isGuranteed()==true? "Y":"N";
			this.goldStarQuote = bol.getGeneralInfo().getQuote();
			
			
			this.pickUp = bol.getGeneralInfo().isPickupRequest()? "Y" : "N";
			
		}
		
		
		
		
		if(bol.getShipperInfo()!=null){
			
			this.shipCompany = bol.getShipperInfo().getCompanyName();
			
			this.shipFirstName = bol.getShipperInfo().getFirstName();
			this.shipLastName  = bol.getShipperInfo().getLastName();
			this.shipLocation   = bol.getShipperInfo().getLocation();
			
			if(bol.getShipperInfo().getPhone()!=null && bol.getShipperInfo().getPhone().replaceAll("\\D+", "").length() >=10){
				
				String shipperPhone  = bol.getShipperInfo().getPhone().replaceAll("\\D+", "");
				
				this.shipAreaCode  = shipperPhone.substring(0, 3);
				
				this.shipExchange   = shipperPhone.substring(3,6);
				this.shipLast4     = shipperPhone.substring(6);
			}
			
			this.shipExt        = bol.getShipperInfo().getPhoneExt();
			
			

			if(bol.getShipperInfo().getFax()!=null && bol.getShipperInfo().getFax().replaceAll("\\D+", "").length() >=10){
				
				String shipperFax    = bol.getShipperInfo().getFax().replaceAll("\\D+", "");
				this.shipFaxAreaCode  = shipperFax.substring(0, 3);
				this.shipFaxExchange   = shipperFax.substring(3,6);
				this.shipFaxLast4     = shipperFax.substring(6);
			}
			
			
			
			this.shipCountry    = bol.getShipperInfo().getCountry()==null? "" : bol.getShipperInfo().getCountry().name();
			
			this.shipStr1Address = bol.getShipperInfo().getAddress1();
			this.shipStr2Address = bol.getShipperInfo().getAddress2();
			this.shipCity         = bol.getShipperInfo().getCity();
			this.shipState        = bol.getShipperInfo().getState();
			this.shipZip          = bol.getShipperInfo().getZip();
			
			this.shipperEmail     = bol.getShipperInfo().getEmail();
		}
		
		if(bol.getConsigneeInfo()!=null){
			this.consCompanyName = bol.getConsigneeInfo().getCompanyName();
			
			this.consFirstName = bol.getConsigneeInfo().getFirstName();
			this.consLastName  = bol.getConsigneeInfo().getLastName();
			this.consLocationNumber   = bol.getConsigneeInfo().getLocation();
			
			
			if(bol.getConsigneeInfo().getPhone()!=null && bol.getConsigneeInfo().getPhone().replaceAll("\\D+", "").length() >=10){
				
				String consigneePhone         = bol.getConsigneeInfo().getPhone().replaceAll("\\D+", "");
				
				this.consPnAreaCode  = consigneePhone.substring(0, 3);
				
				this.consPnExchange   = consigneePhone.substring(3,6);
				this.consPnLast4     = consigneePhone.substring(6);
			}
			
			
			
			this.consPnExtension        = bol.getConsigneeInfo().getPhoneExt();
			
			
			if(bol.getConsigneeInfo().getFax()!=null && bol.getConsigneeInfo().getFax().replaceAll("\\D+", "").length() >=10){
				
				String consigneeFax    = bol.getConsigneeInfo().getFax().replaceAll("\\D+", "");
				this.consFnAreaCode  = consigneeFax.substring(0, 3);
				this.consFnExchange   = consigneeFax.substring(3,6);
				this.consFnLast4     = consigneeFax.substring(6);
			}
			
			
			
			this.consCountry    = bol.getConsigneeInfo().getCountry()==null? "" : bol.getConsigneeInfo().getCountry().name();
			
			this.consStAddr1 = bol.getConsigneeInfo().getAddress1();
			this.consStAddr2 = bol.getConsigneeInfo().getAddress2();
			this.consCity         = bol.getConsigneeInfo().getCity();
			this.consState        = bol.getConsigneeInfo().getState();
			this.consZip          = bol.getConsigneeInfo().getZip();
			this.consigneeEmail  = bol.getConsigneeInfo().getEmail();
		}
		
		if(bol.getCommodityInfo()!=null){
			this.hazmatContact = bol.getCommodityInfo().getContactName();
			this.specialInstructions = bol.getCommodityInfo().getSpecialIns();
			this.cube = bol.getCommodityInfo().getTotalCube();
			
			String hazmatContactPhone = bol.getCommodityInfo().getPhone().replaceAll("\\D+", "");
			
			if(hazmatContactPhone.length()>=10){
				this.hazmatAreaCode = hazmatContactPhone.substring(0,3);
				this.hazmatExchange  = hazmatContactPhone.substring(3,6);
				this.hazmatLast4    = hazmatContactPhone.substring(6);
			}
			
			this.hazmatExtension = bol.getCommodityInfo().getPhoneExt();
		}
		
		if(bol.getShippingLabel()!=null){
			this.startLabel = bol.getShippingLabel().getStartLabel();
			this.totalLabel = bol.getShippingLabel().getNumberOfLabel();
			this.labelType  = bol.getShippingLabel().getLabelType();
		}
		
		if(bol.getEmailAndFaxNotification()!=null){
			
			this.emailBolShipper     = bol.getEmailAndFaxNotification().isShipperEmailBolUpdate()?"Y":"N";
			this.emailTrkShipper     = bol.getEmailAndFaxNotification().isShipperEmailTrackingUpdate()? "Y":"N";
			this.emailLabelShipper   = bol.getEmailAndFaxNotification().isShipperEmailShippingLabel()? "Y":"N";
			this.faxBolShipper       = bol.getEmailAndFaxNotification().isShipperFaxBolUpdate()? "Y" : "N";
			
			this.emailBolCons     = bol.getEmailAndFaxNotification().isConsigneeEmailBolUpdate()?"Y":"N";
			this.emailTrkCons     = bol.getEmailAndFaxNotification().isConsigneeEmailTrackingUpdate()? "Y":"N";
			this.emailLabelCons   = bol.getEmailAndFaxNotification().isConsigneeEmailShippingLabel()? "Y":"N";
			this.faxBolConsignee  = bol.getEmailAndFaxNotification().isShipperFaxBolUpdate()? "Y" : "N";
			
			this.emailBolTpty     = bol.getEmailAndFaxNotification().isThirdPartyEmailBolUpdate()?"Y":"N";
			this.emailTrkTpty     = bol.getEmailAndFaxNotification().isThirdPartyEmailTrackingUpdate()? "Y":"N";
			this.emailLabelTpty   = bol.getEmailAndFaxNotification().isThirdPartyEmailShippingLabel()? "Y":"N";
			this.faxBolTpty       = bol.getEmailAndFaxNotification().isShipperFaxBolUpdate()? "Y" : "N";
			
			this.emailBolOther    = bol.getEmailAndFaxNotification().isOtherEmailBolUpdate()?"Y":"N";
			this.emailTrkOther    = bol.getEmailAndFaxNotification().isOtherEmailTrackingUpdate()?"Y":"N";
			this.emailLabelOther  = bol.getEmailAndFaxNotification().isOtherEmailShippingLabel()? "Y":"N";
			this.faxBolOther       = bol.getEmailAndFaxNotification().isOtherFaxBolUpdate()? "Y" : "N";
			
			this.faxBolNumber      = bol.getEmailAndFaxNotification().getOtherFax().replaceAll("\\D+", "");
			
			if(this.faxBolNumber.length()>=10){
				this.otherFaxAreaCode = this.faxBolNumber.substring(0, 3);
				this.otherFaxExchange  = this.faxBolNumber.substring(3, 6);
				this.otherFaxLast4    = this.faxBolNumber.substring(6);
			}
			
			this.emailOtherAddr  = bol.getEmailAndFaxNotification().getOtherEmail();
			
		}
		
		
		if(bol.getBillingInfo()!=null){
			
			
			
			if(bol.getBillingInfo().getBillTo()!=null){
				
				if(bol.getBillingInfo().getBillTo().getPayor()!=null){
					this.payorCode   = bol.getBillingInfo().getBillTo().getPayor().name();
				}
				
				if(bol.getBillingInfo().getBillTo().getFee()!=null){
					this.termsCode   = bol.getBillingInfo().getBillTo().getFee().name();
				}
				
				
				if(bol.getBillingInfo().getBillTo().getBillingAddressInfo()!=null){
					this.tptyCompanyName = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCompanyName();
					
					this.tptyFirstName = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getFirstName();
					this.tptyLastName  = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getLastName();
					this.tptyLocationNumber   = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getLocation();
					
					
					if(bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhone()!=null && bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhone().replaceAll("\\D+", "").length() >=10){
						
						String thirdPartyPhone         = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhone().replaceAll("\\D+", "");
						
						this.tptyPnAreaCode  = thirdPartyPhone.substring(0, 3);
						
						this.tptyPnExchange   = thirdPartyPhone.substring(3,6);
						this.tptyPnLast4     = thirdPartyPhone.substring(6);
					}
					

					this.tptyPnExtension        = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhoneExt();
					
					
					if(bol.getBillingInfo().getBillTo().getBillingAddressInfo().getFax()!=null && bol.getBillingInfo().getBillTo().getBillingAddressInfo().getFax().replaceAll("\\D+", "").length() >=10){
						
						String thirdPartyFax    = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getFax().replaceAll("\\D+", "");
						this.tptyFnAreaCode     = thirdPartyFax.substring(0, 3);
						this.tptyFnExchange     = thirdPartyFax.substring(3,6);
						this.tptyFnLast4        = thirdPartyFax.substring(6);
					}
					
					
					this.tptyCountry    = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCountry()==null? "": bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCountry().name();
					
					this.tptyStAddr1    = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getAddress1();
					this.tptyStAddr2    = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getAddress2();
					this.tptyCity         = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCity();
					this.tptyState        = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getState();
					this.tptyZip          = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getZip();
					this.thirdpartyEmail = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getEmail();
				}
			}
			
			this.codFlg = "N";
			
			if(bol.getBillingInfo().isCodRemitTo() && bol.getBillingInfo().getCodRemitToInfo()!=null){

				this.codFlg = "Y";
				
				this.codAmount       =  bol.getBillingInfo().getCodRemitToInfo().getAmount();
				this.codPaymentType = bol.getBillingInfo().getCodRemitToInfo().getPaymentType()==null? null : bol.getBillingInfo().getCodRemitToInfo().getPaymentType().name(); 
				this.codFeePaidby   = bol.getBillingInfo().getCodRemitToInfo().getFee()==null? null : bol.getBillingInfo().getCodRemitToInfo().getFee().name(); 
				
				if(bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo()!=null){
					this.codname = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCompanyName(); 
					this.codFirstName =  bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getFirstName();
					this.codLastName  = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getLastName();
					this.codLocationNumber = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getLocation();
					
					if(bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getPhone()!=null && bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getPhone().replaceAll("\\D+", "").length() >=10){
						
						String codPhone         = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getPhone().replaceAll("\\D+", "");
						
						this.codPnAreaCode  = codPhone.substring(0, 3);
						
						this.codPnExchange   = codPhone.substring(3,6);
						this.codPnLast4     = codPhone.substring(6);
					}
				
					this.codPnExtension = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getPhoneExt();
					
					this.codCountry = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCountry()==null? null : bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCountry().name();
					this.codStAddr1 = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getAddress1();
					this.codStAddr2 = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getAddress2();
					this.codCity = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCity();
					this.codState = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getState();
					this.codZip = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getZip();
					this.codEmail = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getEmail();
				}
				
			}
			
			
			
		}
		
		
	}
	
	
	public BillOfLading getBillOfLading(){
		
		BillOfLading bol = new BillOfLading();
		
		bol.setDocumentType(DocumentType.getValueOf(bolType));
		
		GeneralInformation genInfo = new GeneralInformation();
		genInfo.setBolNumber(bolNumber);
		genInfo.setBolDate(bolDate);
		genInfo.setAssignProNumber(autoAsignPro.equalsIgnoreCase("Y"));
		
		if(proOrigTerm.equals("")==false && proNumber.equals("")==false){
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
		consignee.setCity(consCity);
		consignee.setState(consState);
		consignee.setZip(consZip);
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
		billTo.setPayor(BillToRole.getValueOf(payorCode));
		billTo.setFee(Fee.getValueOf(termsCode));
		
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
		billingAddressInfo.setCity(tptyCity);
		billingAddressInfo.setState(tptyState);
		billingAddressInfo.setZip(tptyZip);
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
		codAddressInfo.setCity(codCity);
		codAddressInfo.setState(codState);
		codAddressInfo.setZip(codZip);
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
		emailAndFaxNotification.setShipperEmailShippingLabel(emailLabelShipper.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setShipperEmailPickupNotice(false);
		emailAndFaxNotification.setShipperFax(bol.getShipperInfo().getFax());
		emailAndFaxNotification.setShipperFaxBolUpdate(emailBolCons.equalsIgnoreCase("Y"));
		
		
		emailAndFaxNotification.setConsigneeEmail(consigneeEmail);
		emailAndFaxNotification.setConsigneeEmailBolUpdate(emailBolCons.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setConsigneeEmailTrackingUpdate(emailTrkCons.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setConsigneeEmailShippingLabel(emailLabelCons.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setConsigneeEmailPickupNotice(false);
		emailAndFaxNotification.setConsigneeFax(bol.getConsigneeInfo().getFax());
		emailAndFaxNotification.setConsigneeFaxBolUpdate(faxBolConsignee.equalsIgnoreCase("Y"));
		
		
		
		emailAndFaxNotification.setThirdPartyEmail(thirdpartyEmail);
		emailAndFaxNotification.setThirdPartyEmailBolUpdate(emailBolTpty.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setThirdPartyEmailTrackingUpdate(emailTrkTpty.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setThirdPartyEmailShippingLabel(emailLabelTpty.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setThirdPartyEmailPickupNotice(false);
		
		if(tptyFnAreaCode.equals("")==false && tptyFnExchange.equals("")==false && tptyFnLast4.equals("")==false){
			String fax = "("+tptyFnAreaCode+") "+tptyFnExchange+"-"+tptyFnLast4;
			emailAndFaxNotification.setThirdPartyFax(fax);
		}
		
		emailAndFaxNotification.setThirdPartyFaxBolUpdate(faxBolTpty.equalsIgnoreCase("Y"));
		
		
		emailAndFaxNotification.setOtherEmail(emailOtherAddr);
		emailAndFaxNotification.setOtherEmailBolUpdate(emailBolOther.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setOtherEmailTrackingUpdate(emailTrkOther.equalsIgnoreCase("Y"));
		emailAndFaxNotification.setOtherEmailShippingLabel(emailLabelOther.equalsIgnoreCase("Y"));
		// Set from the pickup services
		emailAndFaxNotification.setOtherEmailPickupNotice(false);
		
		if(otherFaxAreaCode.equals("")==false && otherFaxExchange.equals("")==false && otherFaxLast4.equals("")==false){
			String fax = "("+otherFaxAreaCode+") "+otherFaxExchange+"-"+otherFaxLast4;
			emailAndFaxNotification.setOtherFax(fax);
		}
		
		emailAndFaxNotification.setOtherFaxBolUpdate(faxBolOther.equalsIgnoreCase("Y"));
		
		
		
		
		bol.setEmailAndFaxNotification(emailAndFaxNotification);
		
		
		return bol;
		
	}
}
