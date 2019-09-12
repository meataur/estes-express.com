package com.estes.myestes.BillOfLading.web.service.impl;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.web.dao.iface.DraftDAO;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.Draft;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.service.iface.ConverterService;
import com.estes.myestes.BillOfLading.web.service.iface.DraftService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupNotificationService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;


@Service("draftService")
public class DraftServiceImpl implements DraftService {
	
	@Autowired
	private DraftDAO draftDAO;
	
	
	@Autowired
	private PickupRequestRestService pickupRequestRestService;
	
	@Autowired
	private PickupNotificationService pickupNotificationService;
	
	@Autowired
	private ConverterService converterService;
	
	
	@Override
	public Page<Draft> getDraftList(String username, Pageable pageable, BolFilter filter) {
		return draftDAO.getDraftList(username, pageable, filter);
	}

	@Override
	public void createDraft(String username, AuthenticationDetails auth, BillOfLading billOfLading) {
		
		String bolNumber = billOfLading.getGeneralInfo().getBolNumber();
		
		if(bolNumber==null || bolNumber.trim().equals("")){
			throw new BolException(new ServiceResponse("error","BOL number is a required field.","generalInfo.bolNumber"));
		}
		
		/**
		 * Update email and Fax information for Shipper, Consignee & Third Party from EmailAndFaxNotification object
		 * 
		 */
		converterService.updateFaxAndEmailForShipperConsigneeThirdPartyFromEmailAndNotification(billOfLading);
		
		
		/**
		 * Save Draft Header
		 */
		draftDAO.saveDraftHeader(username, billOfLading);
		
		/**
		 * Save Pickup Request Information
		 */

		if(billOfLading.getGeneralInfo().isPickupRequest() && billOfLading.getPickupDetailInfo()!=null){
			/**
			 * Save Pickup information
			 */
			pickupRequestRestService.savePickupInformation(billOfLading.getPickupDetailInfo(), auth.getAccessToken(), BolType.DRAFT, bolNumber);
			/**
			 * Save Pickup Notification
			 */
			pickupNotificationService.updatePickupNotification(auth, billOfLading.getEmailAndFaxNotification(), billOfLading.getPickupDetailInfo().isAccepted(), billOfLading.getPickupDetailInfo().isCompleted(), BolType.DRAFT,bolNumber);
			
		}else{
			pickupRequestRestService.deletePickupInformation(auth.getAccessToken(), BolType.DRAFT, bolNumber);
		}
		
		
		
		/**
		 * Save Commodities
		 */
		draftDAO.saveDraftCommodities(username, bolNumber, billOfLading);
		
		/**
		 * Save References
		 */
		draftDAO.saveDraftReferences(username, bolNumber, billOfLading);
		
		/**
		 * Save Accessorials
		 */
		draftDAO.saveDraftAccessorials(username, bolNumber, billOfLading);
		
	}

	@Override
	public BillOfLading getDraftByBolNumber(String username, AuthenticationDetails auth, String bolNumber) {
		/**
		 * Check the draft exists
		 */
		if(draftDAO.isDraftExist(username, bolNumber)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "Draft doesn't exist!");
		}
		
		
		BillOfLading billOfLading =  draftDAO.getBillOfLading(username, bolNumber);
		
		/**
		 *  Retrieve Pickup Request Information & Set in billOfLading
		 */
		billOfLading.setPickupDetailInfo(pickupRequestRestService.getPickupInformation(auth.getAccessToken(), BolType.DRAFT, bolNumber));
		
		
		if(billOfLading.getPickupDetailInfo()!=null 
				&& billOfLading.getPickupDetailInfo().getStartTime()!=null
				&& billOfLading.getPickupDetailInfo().getEndTime()!=null){
			billOfLading.getGeneralInfo().setPickupRequest(true);
			/**
			 * Retrieve Pickup Notification from Pickup Services
			 */
			EmailAndFaxNotification emailAndFaxNotification= pickupNotificationService.getPickupNotification(auth, billOfLading.getEmailAndFaxNotification(), BolType.DRAFT, bolNumber);
			billOfLading.setEmailAndFaxNotification(emailAndFaxNotification);
		}else{
			billOfLading.setPickupDetailInfo(null);
		}

		return billOfLading;
	}

	@Override
	public void deleteDraft(String username, String accessToken, String bolNumber) {
		
		/**
		 * Check the draft exists
		 */
		if(draftDAO.isDraftExist(username, bolNumber)==false){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "Draft doesn't exist!");
		}
		
		
		
		/**
		 * Delete Accessorials
		 */
		draftDAO.deleteDraftAccessorial(username, bolNumber);
		
		
		/**
		 * Delete Commodities
		 */
		draftDAO.deleteDraftCommodity(username, bolNumber);
		
		/**
		 * Delete References
		 */
		draftDAO.deleteDraftReference(username, bolNumber);
		
		/**
		 * Delete Pickup Request Information
		 */
		
		pickupRequestRestService.deletePickupInformation(accessToken, BolType.DRAFT, bolNumber);
		
		/**
		 * Delete Draft Header
		 */
		draftDAO.deleteDraftHeader(username, bolNumber);
	}

}
