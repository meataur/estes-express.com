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
import com.estes.myestes.BillOfLading.web.dao.iface.TemplateDAO;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.TemeplateFilter;
import com.estes.myestes.BillOfLading.web.dto.Template;
import com.estes.myestes.BillOfLading.web.service.iface.ConverterService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupNotificationService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;
import com.estes.myestes.BillOfLading.web.service.iface.TemplateService;


@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private TemplateDAO templateDAO;
	

	@Autowired
	private PickupRequestRestService pickupRequestRestService;
	
	@Autowired
	private PickupNotificationService pickupNotificationService;
	
	@Autowired
	private ConverterService converterService;
	
	
	@Override
	public Page<Template> getTemplates(String username, Pageable pageable, TemeplateFilter filter) {
		return templateDAO.getTemplatetList(username, pageable, filter);
	}

	@Override
	public void createTemplate(String username, AuthenticationDetails auth, String template,BillOfLading billOfLading) {
		
		if(template==null || template.trim().equals("")){
			throw new BolException(new ServiceResponse("error","Template name is a required.","template"));
		}
		
		/**
		 * Update email and Fax information for Shipper, Consignee & Third Party from EmailAndFaxNotification object
		 * 
		 */
		converterService.updateFaxAndEmailForShipperConsigneeThirdPartyFromEmailAndNotification(billOfLading);
		
		
		/**
		 * Save Template Header
		 */
		templateDAO.saveTemplateHeader(username, template, billOfLading);
		
		/**
		 * Save Pickup Request Information
		 */
		if(billOfLading.getGeneralInfo().isPickupRequest() && billOfLading.getPickupDetailInfo()!=null){
			pickupRequestRestService.savePickupInformation(billOfLading.getPickupDetailInfo(), auth.getAccessToken(), BolType.TEMPLATE, template);
		
			/**
			 * Save Pickup Notification
			 */
			pickupNotificationService.updatePickupNotification(auth, billOfLading.getEmailAndFaxNotification(), billOfLading.getPickupDetailInfo().isAccepted(), billOfLading.getPickupDetailInfo().isCompleted(), BolType.TEMPLATE,template);
		
		}else{
			pickupRequestRestService.deletePickupInformation( auth.getAccessToken(), BolType.TEMPLATE, template);
		}
		
		/**
		 * Save Template Commodities
		 */
		templateDAO.saveTemplateCommodities(username, template, billOfLading);
		/**
		 * Save Template References
		 */
		templateDAO.saveTemplateReferences(username, template, billOfLading);
		
		/**
		 * Save Template Accessorials
		 */
		templateDAO.saveTemplateAccessorials(username, template, billOfLading);
	}

	@Override
	public BillOfLading getTemplateByTemplateName(String username, AuthenticationDetails auth, String templateName) {
		/**
		 * Check the template exists
		 */
		if(templateDAO.isTemplateExist(username, templateName)==false){
			throw new BolException(HttpStatus.NOT_FOUND,DEFAULT_ERROR_CODE,"Template doesn't exist!");
		}
		
		BillOfLading billOfLading = templateDAO.getBillOfLading(username, templateName);
		
		/**
		 * Retrieve Pickup Request Information & Set
		 */
		
		billOfLading.setPickupDetailInfo(pickupRequestRestService.getPickupInformation(auth.getAccessToken(), BolType.TEMPLATE, templateName));
		
		
		if(billOfLading.getPickupDetailInfo()!=null 
				&& billOfLading.getPickupDetailInfo().getStartTime()!=null
				&& billOfLading.getPickupDetailInfo().getEndTime()!=null){
			billOfLading.getGeneralInfo().setPickupRequest(true);
			/**
			 * Retrieve Pickup Notification from Pickup Services
			 */
			EmailAndFaxNotification emailAndFaxNotification= pickupNotificationService.getPickupNotification(auth, billOfLading.getEmailAndFaxNotification(), BolType.TEMPLATE, templateName);
			billOfLading.setEmailAndFaxNotification(emailAndFaxNotification);
		}else{
			billOfLading.setPickupDetailInfo(null);
		}
		
		return billOfLading;
	}

	@Override
	public void deleteTemplate(String username, String accessToken, String template) {
		
		/**
		 * Check the template exists
		 */
		if(templateDAO.isTemplateExist(username, template)==false){
			throw new BolException(HttpStatus.NOT_FOUND,DEFAULT_ERROR_CODE,"Template doesn't exist!");
		}
		
		/**
		 * Delete Accessorials
		 */
		templateDAO.deleteTemplateAccessorial(username, template);
		/**
		 * Delete Commodities
		 */
		templateDAO.deleteTemplateCommodity(username, template);
		
		/**
		 * Delete References
		 */
		templateDAO.deleteTemplateReference(username, template);
		
		/**
		 * Delete Pickup Request Information
		 */
		pickupRequestRestService.deletePickupInformation(accessToken, BolType.TEMPLATE,template);
		
		/**
		 * Delete Template Head
		 */
		templateDAO.deleteTemplateHeader(username, template);
		
	}
}
