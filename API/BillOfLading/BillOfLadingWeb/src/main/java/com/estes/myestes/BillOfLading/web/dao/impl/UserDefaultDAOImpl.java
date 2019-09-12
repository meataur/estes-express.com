package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.*;
import static com.estes.myestes.BillOfLading.web.dao.sql.UserDefaultQuery.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.BillOfLading.util.QueryUtil;
import com.estes.myestes.BillOfLading.web.dao.iface.CommonDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.UserDefaultDAO;
import com.estes.myestes.BillOfLading.web.dao.mapper.AccessorialMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.AddressInformationMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.BillToMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.CommodityInformationMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.CommodityMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.EmailAndFaxNotificationMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.ShippingLabelMapper;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.BillTo;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;

@Repository("userDefaultDAO")
public class UserDefaultDAOImpl implements UserDefaultDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CommonDAO commonDAO;
	
	@Override
	public AddressInformation getUserShipperInformation(String username) {
		return jdbcTemplate.queryForObject(GET_USER_SHIPPER_INFORMATION,new Object[]{username}, new AddressInformationMapper());
		
	}
	
	
	@Override
	public AddressInformation getUserConsigneeInformation(String username) {
		return jdbcTemplate.queryForObject(GET_USER_CONSIGNEE_INFORMATION,new Object[]{username}, new AddressInformationMapper());
		
	}


	@Override
	public BillTo getUserBillingInformation(String username) {
		
		return jdbcTemplate.queryForObject(GET_USER_BILLING_INFORMATION,new Object[]{username}, new BillToMapper());
	}


	@Override
	public CommodityInformation getUserCommodityInformation(String username) {
		CommodityInformation commodityInfo = null;
		try{
			commodityInfo = jdbcTemplate.queryForObject(GET_USER_COMMODITY_INFORMATION, new Object[]{username}, new CommodityInformationMapper());
		}catch(Exception ex){
			commodityInfo = new CommodityInformation();
		}
		List<Commodity> commodityList = getUserCommodityList(username);
		commodityInfo.setCommodityList(commodityList);
		return commodityInfo;
	}
	
	@Override
	public List<Commodity> getUserCommodityList(String username) {
		List<Commodity> commodityList = jdbcTemplate.query(GET_USER_COMMODITIES, new Object[]{username}, new CommodityMapper());
		return commodityList;
	}


	@Override
	public List<Accessorial> getUserAccessorialInformation(String username) {
		List<Accessorial> accessorials = jdbcTemplate.query(GET_USER_ACCESSORIALS, new Object[]{username}, new AccessorialMapper());
		return accessorials;
	}


	@Override
	public ShippingLabel getUserShippingLabelInformation(String username) {
		ShippingLabel shippingLabel = jdbcTemplate.queryForObject(GET_USER_SHIPPING_LABEL, new Object[]{username}, new ShippingLabelMapper());
		return shippingLabel;
	}


	@Override
	public EmailAndFaxNotification getUserNotificationInformation(String username) {
		EmailAndFaxNotification emailAndFaxNotifications = jdbcTemplate.queryForObject(GET_USER_NOTIFICATIONS, new Object[]{username}, new EmailAndFaxNotificationMapper());
		return emailAndFaxNotifications;
	}

	
	@Override
	public void updateUserShipperInformation(String username, AddressInformation shipper) {
		Map<String,String>  params = new LinkedHashMap<>();
		params.put("BOL_SHIPPER_CO_NAME", shipper.getCompanyName());
		params.put("BOL_SHIPPER_FIRST_NAME",shipper.getFirstName());
		params.put("BOL_SHIPPER_LAST_NAME", shipper.getLastName());
		params.put("BOL_SHIPPER_LOCATION", shipper.getLocation());
		params.put("BOL_SHIPPER_PHONE", shipper.formatPhone());
		params.put("BOL_SHIPPER_EXTENSION", shipper.getPhoneExt());
		params.put("BOL_SHIPPER_FAX", shipper.formatFax());
		params.put("BOL_SHIPPER_COUNTRY", shipper.getCountry().name());
		params.put("BOL_SHIPPER_ADDRESS1", shipper.getAddress1());
		params.put("BOL_SHIPPER_ADDRESS2",shipper.getAddress2());
		params.put("BOL_SHIPPER_CITY", shipper.getCity());
		params.put("BOL_SHIPPER_STATE",shipper.getState());
		params.put("BOL_SHIPPER_ZIP", shipper.getZip());
		params.put("BOL_SHIPPER_EMAIL", shipper.getEmail());
		params.put("BOL_USER_NAME", username);
		

		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				entry.setValue("");
			}
		}
		
		String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);	
		
	}


	@Override
	public void updateUserConsigneeInformation(String username, AddressInformation consignee) {
		Map<String,String>  params = new LinkedHashMap<>();
		params.put("BOL_CONSIGNEE_CO_NAME", consignee.getCompanyName());
		params.put("BOL_CONSIGNEE_FIRST_NAME",consignee.getFirstName());
		params.put("BOL_CONSIGNEE_LAST_NAME", consignee.getLastName());
		params.put("BOL_CONSIGNEE_LOCATION", consignee.getLocation());
		params.put("BOL_CONSIGNEE_PHONE", consignee.formatPhone());
		params.put("BOL_CONSIGNEE_EXTENSION", consignee.getPhoneExt());
		params.put("BOL_CONSIGNEE_FAX", consignee.formatFax());
		params.put("BOL_CONSIGNEE_COUNTRY", consignee.getCountry().name());
		params.put("BOL_CONSIGNEE_ADDRESS1", consignee.getAddress1());
		params.put("BOL_CONSIGNEE_ADDRESS2",consignee.getAddress2());
		params.put("BOL_CONSIGNEE_CITY", consignee.getCity());
		params.put("BOL_CONSIGNEE_STATE",consignee.getState());
		params.put("BOL_CONSIGNEE_ZIP", consignee.getZip());
		params.put("BOL_CONSIGNEE_EMAIL", consignee.getEmail());
		params.put("BOL_USER_NAME", username);
		
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				entry.setValue("");
			}
		}
		
		
		String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);	
	}


	@Override
	public void updateUserCommodityInformation(String username, CommodityInformation commodityInformation) {
		
		Map<String, String> params = new LinkedHashMap<>();
		
		params.put("BOL_TOT_CUBE", commodityInformation.getTotalCube());
		
		params.put("BOL_SPEC_INSTR", commodityInformation.getSpecialIns());
		params.put("BOL_USER_NAME", username);
		
		
		String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);
		
		
		
		updateUserCommodity(username, commodityInformation);
		
		
		
	}
	
	private void updateUserCommodity(String username, CommodityInformation commodityInformation){
		Map<String, Object> params = new LinkedHashMap<>();
		
		params.put("BOL_USER_NAME", username);
		params.put("HAZMAT_CONTRACT", commodityInformation.getContactName());
		params.put("HAZMAT_PHONE", commodityInformation.formatPhoneForDb());
		params.put("HAZMAT_PHONE_EXTENSION", commodityInformation.getPhoneExt());
		
		
		/**
		 * Delete Previous Commodities 
		 */
		String deleteQuery = QueryUtil.prepareDeleteQuery(DATALIB, USER_DEFAULT_COMMODITY, new LinkedHashSet<>(Arrays.asList("BOL_USER_NAME")));
		jdbcTemplate.update(deleteQuery, new Object[]{username});
		
		/**
		 * Insert Each new Commodities 
		 */
		int i =1;
		for(Commodity commodity : commodityInformation.getCommodityList()){
			
			params.put("SEQUENCE_ID",i);
			params.put("HAZ_MAT_FLAG", commodity.isHazmat()? "Y": "N");
			params.put("NUMBER_OF_PACKAGES", commodity.getGoodsUnit());
			params.put("PACKAGE_TYPE", commodity.getGoodsType().getCode());
			params.put("SHIPMENT_WEIGHT", commodity.getGoodsWeight());
			params.put("SHIPMENT_CLASS", commodity.getShipmentClass().getCode());
			params.put("NAT_MOTOR_FGT_CLASS", commodity.getNmfc());
			params.put("NMFC_EXTENSION", commodity.getNmfcExt());
			params.put("VICS_NUMBER_OF_PACKAGES", commodity.getNumberOfPackage());
			params.put("VICS_PACKAGE_TYPE", commodity.getPackageType());
			params.put("COMMODITY_DESCRIPTION",commodity.getDescription());
		
			
			String insertQuery = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_COMMODITY, params.keySet());
			
			Object[] values = params.values().toArray();
			
			jdbcTemplate.update(insertQuery,values);	
			i++;
		}
	}
	
	


	@Override
	public void updateUserBillingInformation(String username, BillTo billingInformation) {
		
		
		Map<String, String> params = new LinkedHashMap<>();
		params.put("BOL_BILL_TO_THRD", billingInformation.getPayor().name());
		params.put("BOL_BILL_TO_TERM", billingInformation.getFee().name());
		params.put("BOL_BILL_TO_CO_NAME", billingInformation.getBillingAddressInfo().getCompanyName());
		params.put("BOL_BILL_TO_FIRST_NAME", billingInformation.getBillingAddressInfo().getFirstName());
		params.put("BOL_BILL_TO_LAST_NAME", billingInformation.getBillingAddressInfo().getLastName());
		params.put("BOL_BILL_TO_LOCATION", billingInformation.getBillingAddressInfo().getLocation());
		params.put("BOL_BILL_TO_PHONE", billingInformation.getBillingAddressInfo().formatPhone());
		params.put("BOL_BILL_TO_EXTENSION", billingInformation.getBillingAddressInfo().getPhoneExt());
		params.put("BOL_BILL_TO_FAX", billingInformation.getBillingAddressInfo().formatFax());
		params.put("BOL_BILL_TO_COUNTRY", billingInformation.getBillingAddressInfo().getCountry().name());
		params.put("BOL_BILL_TO_ADDRESS1",billingInformation.getBillingAddressInfo().getAddress1());
		params.put("BOL_BILL_TO_ADDRESS2", billingInformation.getBillingAddressInfo().getAddress2());
		params.put("BOL_BILL_TO_CITY", billingInformation.getBillingAddressInfo().getCity());
		params.put("BOL_BILL_TO_STATE", billingInformation.getBillingAddressInfo().getState());
		params.put("BOL_BILL_TO_ZIP", billingInformation.getBillingAddressInfo().getZip());
		params.put("BOL_BILL_TO_EMAIL", billingInformation.getBillingAddressInfo().getEmail());
		params.put("BOL_USER_NAME", username);
		
		
		
		String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);	
	}


	@Override
	public void updateUserAccessorialInformation(String username, List<Accessorial> accessorials) {
		Map<String,String>  params = new LinkedHashMap<>();
		params.put("ACCESSORIAL_CODE","");
		params.put("BOL_USER_NAME", username);
		
		
		/**
		 * Delete Previous Accessorials 
		 */
		String deleteQuery = QueryUtil.prepareDeleteQuery(DATALIB, USER_DEFAULT_ACCESSORIAL, new LinkedHashSet<>(Arrays.asList("BOL_USER_NAME")));
		jdbcTemplate.update(deleteQuery, new Object[]{username});
		
		/**
		 * Insert Each new Accessorials 
		 */
		for(Accessorial accessorial : accessorials){
			params.put("ACCESSORIAL_CODE", accessorial.getCode());

			String insertQuery = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_ACCESSORIAL, params.keySet());
			
			Object[] values = params.values().toArray();

			jdbcTemplate.update(insertQuery,values);	
		}
		
	}


	@Override
	public void updateUserShippingLabelInformation(String username, ShippingLabel shippingLabel) {
		Map<String,String>  params = new LinkedHashMap<>();
		params.put("BOL_BILL_TO_LABEL_OPT",shippingLabel.getLabelType());
		params.put("BOL_BILL_TO_LABEL_SW",shippingLabel.getStartLabel());
	    params.put("BOL_BILL_TO_LABEL_NBR",shippingLabel.getNumberOfLabel());
	    params.put("BOL_USER_NAME", username);
	    
	    String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);	
		
	}


	@Override
	public void updateUserNotificationInformation(String username, EmailAndFaxNotification emailAndFaxNotification) {
		
		Map<String, String> params = new LinkedHashMap<>();
		
		params.put("BOL_SHIPPER_EMAIL",emailAndFaxNotification.getShipperEmail()); 
		params.put("BOL_ES_BOL",emailAndFaxNotification.isShipperEmailBolUpdate()? "Y" : "N");
		params.put("BOL_ES_TRACKING_UPDATES", emailAndFaxNotification.isShipperEmailTrackingUpdate()? "Y" : "N");
		params.put("BOL_ES_PICKUP_NOTICE", emailAndFaxNotification.isShipperEmailPickupNotice()? "Y" : "N");
		params.put("BOL_ES_SHIPPING_LABELS",emailAndFaxNotification.isShipperEmailShippingLabel()? "Y" : "N"); 
		params.put("BOL_FS_BOL",emailAndFaxNotification.isShipperFaxBolUpdate()? "Y" : "N"); 
		params.put("BOL_SHIPPER_FAX",emailAndFaxNotification.getShipperFax().replaceAll("\\D+","")); 

		params.put("BOL_CONSIGNEE_EMAIL",emailAndFaxNotification.getConsigneeEmail()); 
		params.put("BOL_EC_BOL",emailAndFaxNotification.isConsigneeEmailBolUpdate()? "Y" : "N"); 
		params.put("BOL_EC_TRACKING_UPDATES",emailAndFaxNotification.isConsigneeEmailTrackingUpdate()? "Y" : "N");
		params.put("BOL_EC_PICKUP_NOTICE",emailAndFaxNotification.isConsigneeEmailPickupNotice()? "Y" : "N"); 
		params.put("BOL_EC_SHIPPING_LABELS",emailAndFaxNotification.isConsigneeEmailShippingLabel()? "Y" : "N");
		params.put("BOL_FC_BOL",emailAndFaxNotification.isConsigneeFaxBolUpdate()? "Y" : "N"); 
		params.put("BOL_CONSIGNEE_FAX",emailAndFaxNotification.getConsigneeFax().replaceAll("\\D+",""));
			 
		params.put("BOL_BILL_TO_EMAIL",emailAndFaxNotification.getThirdPartyEmail()); 
		params.put("BOL_TP_BOL",emailAndFaxNotification.isThirdPartyEmailBolUpdate()? "Y" : "N"); 
		params.put("BOL_TP_TRACKING_UPDATES",emailAndFaxNotification.isThirdPartyEmailTrackingUpdate()? "Y" : "N");
		params.put("BOL_TP_PICKUP_NOTICE",emailAndFaxNotification.isThirdPartyEmailPickupNotice()? "Y" : "N"); 
		params.put("BOL_TP_SHIPPING_LABEL",emailAndFaxNotification.isThirdPartyEmailShippingLabel()? "Y" : "N");
		params.put("BOL_FT_BOL",emailAndFaxNotification.isThirdPartyFaxBolUpdate()? "Y" : "N"); 
		params.put("BOL_BILL_TO_FAX",emailAndFaxNotification.getThirdPartyFax().replaceAll("\\D+","")); 
			 
		params.put("BOL_EO_EMAIL_ADDRESS",emailAndFaxNotification.getOtherEmail());
		params.put("BOL_EO_BOL",emailAndFaxNotification.isOtherEmailBolUpdate()? "Y" : "N");
		params.put("BOL_EO_TRACKING_UPDATES",emailAndFaxNotification.isOtherEmailTrackingUpdate()? "Y" : "N");
		params.put("BOL_EO_PICKUP_NOTICE",emailAndFaxNotification.isOtherEmailPickupNotice()? "Y" : "N");
		params.put("BOL_EO_SHIPPING_LABELS",emailAndFaxNotification.isOtherEmailShippingLabel()? "Y" : "N");
		params.put("BOL_FO_BOL",emailAndFaxNotification.isOtherFaxBolUpdate()? "Y" : "N");
		params.put("BOL_FO_PHONE",emailAndFaxNotification.getOtherFax().replaceAll("\\D+",""));
		params.put("BOL_USER_NAME", username);
		
		String query = "";
		
		if(commonDAO.hasExist(COUNT_USER_DEFAULT_HEAD, new Object[]{username})){
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("BOL_USER_NAME");
			query = QueryUtil.prepareUpdateQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet(),whereParams);
		}else{
			
			query = QueryUtil.prepareInsertQuery(DATALIB, USER_DEFAULT_HEAD, params.keySet());
		}

		Object[] values = params.values().toArray();

		jdbcTemplate.update(query,values);	
		
		
	}
	
}
