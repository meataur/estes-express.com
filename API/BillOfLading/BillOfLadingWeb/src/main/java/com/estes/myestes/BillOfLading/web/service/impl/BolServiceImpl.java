package com.estes.myestes.BillOfLading.web.service.impl;


import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dao.iface.BolDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.DraftDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.PickupRequestValidatorDAO;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Bol;
import com.estes.myestes.BillOfLading.web.dto.BolDocument;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.BolResponse;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;
import com.estes.myestes.BillOfLading.web.service.iface.BolService;
import com.estes.myestes.BillOfLading.web.service.iface.ConverterService;
import com.estes.myestes.BillOfLading.web.service.iface.DraftService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupNotificationService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;
import com.google.gson.Gson;

@Service("bolService")
public class BolServiceImpl implements BolService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BolDAO bolDAO;
	
	@Autowired
	private DraftDAO draftDAO;
	
	@Autowired
	private PickupRequestRestService pickupRequestRestService;
	
	
	@Autowired
	private PickupNotificationService pickupNotificationService;
	
	@Autowired
	private PickupRequestValidatorDAO pickupRequestValidatorDAO;
	
	@Autowired
	private ConverterService converterService;
	
	@Autowired
	private DraftService draftService;
	
	@Override
	public Bol getBolById(String username, int bolId) {
		return bolDAO.getBolById(username, bolId);
	}

	@Override
	public Page<Bol> getBolHistory(String username, Pageable pageable, BolFilter filter) {
		return bolDAO.getBolHistory(username, pageable, filter);
	}

	@Override
	public BolResponse createBol(AuthenticationDetails auth,BillOfLading billOfLading, String updateType, String templateName) {
		
		/**
		 * Update email and Fax information for Shipper, Consignee & Third Party from EmailAndFaxNotification object
		 * 
		 */
		converterService.updateFaxAndEmailForShipperConsigneeThirdPartyFromEmailAndNotification(billOfLading);
		
		String timestamp = EstesUtil.getTimestamp();
		
		String bolNumber = billOfLading.getGeneralInfo().getBolNumber();
		
	
		/**
		 * Save BOL Header
		 */
		bolDAO.saveBolHeader(auth.getUsername(), auth.getRandom(), timestamp, billOfLading, updateType, templateName);
		
		/**
		 * As Service level is not updating by above BOL header saving because of there is no parameter in that SPROC
		 * It is needed to store/update the value in BOL HEADER STAGING TABLE EBG10P200 (200 series are Staging tables) 
		 */
		String serviceLevel = billOfLading.getGeneralInfo().isGuranteed()? "Y" : "";
		
		bolDAO.saveServiceLevel(serviceLevel, auth.getUsername(), billOfLading.getBolId());

		/**
		 * Save BOL Header as Draft 
		 */
		draftDAO.saveDraftHeader(auth.getUsername(), billOfLading);
		
		PickupRequest pickupRequest = null;
		
		/**
		 * Check PickupRequest
		 */
		if(billOfLading.getGeneralInfo().isPickupRequest()){
			/**
			 * Check if pickupRequest is null
			 */
			if(billOfLading.getPickupDetailInfo()==null){
				/**
				 * If null throw exception
				 */
				throw new BolException(DEFAULT_ERROR_CODE, "Pickup Details are required","pickupDetailInfo");
				
			}
			
			pickupRequest = converterService.conver(billOfLading,auth);
			
			/**
			 * Validate PickupRequest
			 */
			
			pickupRequestValidatorDAO.validatePickupRequest(pickupRequest, billOfLading, auth);
			
			
			/**
			 * Save Draft Pickup Request Information
			 */
			pickupRequestRestService.savePickupInformation(billOfLading.getPickupDetailInfo(), auth.getAccessToken(), BolType.DRAFT,bolNumber);
			
			
			/**
			 * Save Draft Pickup Notification
			 */
			if(billOfLading.getGeneralInfo().isPickupRequest() 
					&& billOfLading.getPickupDetailInfo()!=null
					&& billOfLading.getEmailAndFaxNotification()!=null){
				pickupNotificationService.updatePickupNotification(auth, billOfLading.getEmailAndFaxNotification(), billOfLading.getPickupDetailInfo().isAccepted(), billOfLading.getPickupDetailInfo().isCompleted(), BolType.DRAFT,bolNumber);
			}
			
			
		}
			
		
		/**
		 * Save Commodities
		 */
		
		
		bolDAO.saveCommodities(auth.getUsername(), auth.getRandom(), timestamp, billOfLading, updateType, templateName);
		
		
		/**
		 * Save Commodities as Draft 
		 */
		draftDAO.saveDraftCommodities(auth.getUsername(),  bolNumber, billOfLading);
		
		/**
		 * Save Reference Numbers
		 */
		 
		bolDAO.saveReferences(auth.getUsername(), auth.getRandom(), timestamp, billOfLading, updateType, templateName);
		
		
		/**
		 * Save References as Draft 
		 */
		draftDAO.saveDraftReferences(auth.getUsername(), bolNumber, billOfLading);
		
		
		/**
		 * Save Accessorials
		 * 
		 */
		
		bolDAO.saveAccessorials(auth.getUsername(), auth.getRandom(), timestamp, billOfLading, updateType, templateName);
		
		/**
		 * Save Accessorials as Draft 
		 */
		draftDAO.saveDraftAccessorials(auth.getUsername(), bolNumber, billOfLading);
		
		/**
		 * Write BOL in final Table
		 */
		bolDAO.writeFinalBol(auth.getRandom(), timestamp);
		
		
		
		/**
		 *  delete draft BOL
		 */
		try{
			draftService.deleteDraft(auth.getUsername(), auth.getAccessToken(), bolNumber);
		}catch(Exception ex){
			ESTESLogger.log(ESTESLogger.ERROR, "at class - "+getClass(), " at method - createBol() at line no - 191 : ","After creating BOL successfully, Draft created during BOL creation could not be deleted. BOL# "+bolNumber+" USER_NAME="+auth.getUsername()); 
		}
		
		
		
		/**
		 * Save Pickup Request Information
		 * 
		 */
		
		BolResponse bolResponse = new BolResponse();
		
		
		if(pickupRequest!=null){
			/**
			 * Set PickupRequest Default Response
			 * 
			 */
			bolResponse.setPickupRequest(pickupRequest);

			try {
				/**
				 * Create new Pickup Request
				 */
				ResponseEntity<PickupRequest> response = pickupRequestRestService.savePickupRequest(auth.getAccessToken(), pickupRequest);
				
				if(response.hasBody()){
					pickupRequest = response.getBody();
					/**
					 * Set PickupRequest in BolResponse
					 */
					bolResponse.setPickupRequest(pickupRequest);
					
					/**
					 * Save Pickup Information to MSSQL Database to retrieve history
					 */
					pickupRequestRestService.savePickupInformation(billOfLading.getPickupDetailInfo(), auth.getAccessToken(), BolType.HISTORY, Integer.toString(billOfLading.getBolId()));
					
					/**
					 * Save Pickup Notification
					 */
					pickupNotificationService.updatePickupNotification(auth, billOfLading.getEmailAndFaxNotification(), billOfLading.getPickupDetailInfo().isAccepted(), billOfLading.getPickupDetailInfo().isCompleted(), BolType.HISTORY,Integer.toString(billOfLading.getBolId()));
					
					/**
					 * Delete Draft Pickup 
					 */
					pickupRequestRestService.deletePickupInformation(auth.getAccessToken(), BolType.DRAFT, bolNumber);
					
				}
			}catch(RestClientResponseException ex){
				
				/**
				 * Exceptions on creating new Pickup Request
				 */
				
				List<ServiceResponse> serviceResponseList = new ArrayList<>();
				/**
				 * for Bad Request (400), it returns List<ServiceResponse>
				 */
				
				if(ex.getRawStatusCode()==HttpStatus.BAD_REQUEST.value()){
					
					ServiceResponse[] serviceResponses = new Gson().fromJson(ex.getResponseBodyAsString(),ServiceResponse[].class);
					serviceResponseList = Arrays.asList(serviceResponses);
					/**
					 * PickupRequest returns the 
					 */
					for(ServiceResponse serviceResponse: serviceResponseList){
						if(env.containsProperty(serviceResponse.getFieldName())){
							serviceResponse.setFieldName(env.getProperty(serviceResponse.getFieldName()));
						}
					}
				}else{
					/**
					 * It returns Single Service Response
					 */
					ServiceResponse serviceResponse = new Gson().fromJson(ex.getResponseBodyAsString(),ServiceResponse.class);
					serviceResponseList.add(serviceResponse);
				}
				
				bolResponse.setPickupErrors(serviceResponseList);
				
			}

		}
		

		return bolResponse;

	}

	@Override
	public BillOfLading getBillOfLadingById(String username, AuthenticationDetails auth, int id) {
		
		/**
		 * Check the BOL exists
		 */
		if(bolDAO.isBolExist(username, id)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "BOL doesn't exist!");
		}
		
		BillOfLading billOfLading =  bolDAO.getBillOfLadingById(username, id);
		
		/**
		 * Retrieve Pickup Request Information
		 */
		
		billOfLading.setPickupDetailInfo(pickupRequestRestService.getPickupInformation(auth.getAccessToken(), BolType.HISTORY, Integer.toString(id)));
		

		if(billOfLading.getPickupDetailInfo()!=null 
				&& billOfLading.getPickupDetailInfo().getStartTime()!=null
				&& billOfLading.getPickupDetailInfo().getEndTime()!=null){
			/**
			 * Set this BOL has pickup request information
			 */
			billOfLading.getGeneralInfo().setPickupRequest(true);
			/**
			 * Retrieve Pickup Notification from Pickup Services
			 */
			EmailAndFaxNotification emailAndFaxNotification= pickupNotificationService.getPickupNotification(auth, billOfLading.getEmailAndFaxNotification(), BolType.HISTORY, Integer.toString(id));
			billOfLading.setEmailAndFaxNotification(emailAndFaxNotification);
		}else{
			/**
			 * Set This BOL does not have any Pickup Information
			 */
			billOfLading.setPickupDetailInfo(null);
		}
		
		return billOfLading;
	}

	@Override
	public void cancelBol(String username, String accessToken, int id) {
		
		/**
		 * Check the BOL exists
		 */
		if(bolDAO.isBolExist(username, id)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "BOL doesn't exist!");
		}
		
		
		bolDAO.cancelBol(username, id);
		/**
		 * Delete Pickup Inormation
		 */
		pickupRequestRestService.deletePickupInformation(accessToken, BolType.HISTORY, Integer.toString(id));
	}

	@Override
	public BolDocument createBolLabelDocument(String username, int id, ShippingLabel shippingLabel) {
		/**
		 * Check the BOL exists
		 */
		if(bolDAO.isBolExist(username, id)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "BOL doesn't exist!");
		}
		
		return bolDAO.createBolLabelDocument(username, id, shippingLabel);
	}
	

	@Override
	public BolDocument getBolLabelDocument(String username, int id) {
		/**
		 * Check the BOL exists
		 */
		if(bolDAO.isBolExist(username, id)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "BOL doesn't exist!");
		}
		
		return  bolDAO.getBolLabelDocument(username, id);
	}

	@Override
	public BolDocument getBolDocument(String username, int id) {
		/**
		 * Check the BOL exists
		 */
		if(bolDAO.isBolExist(username, id)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "BOL doesn't exist!");
		}
		
		return bolDAO.getBolDocument(username, id);
	}


}
