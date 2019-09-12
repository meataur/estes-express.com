/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shiptrack.dao.iface.EmailUpdateDAO;
import com.estes.myestes.shiptrack.dao.iface.ErrorDAO;
import com.estes.myestes.shiptrack.dto.EmailStatusRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.EmailUpdateService;

@Service("emailService")
@Scope("prototype")
@Transactional
public class EmailUpdateServiceImpl implements EmailUpdateService
{
	@Autowired
	private EmailUpdateDAO emailStatusDAO;
	@Autowired
	private ErrorDAO errorDAO;

	/**
	 * Create a new service
	 */
	public EmailUpdateServiceImpl() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.shiptrack.service.iface.EmailUpdateService#requestUpdates()
	 */
	@Override
	public List<ServiceResponse> requestUpdates(EmailStatusRequest statusReq) throws ShipTrackException
	{
		String[] emailAddressList = splitEmailAddresses(statusReq.getAddresses());
		List<ServiceResponse> responses = new ArrayList<>();

		List<String> proNumbers = new ArrayList<>();
		
		List<String> emails = new ArrayList<>();
		
		List<String> alreadyMonitoringPros   = new ArrayList<>();
		List<String> alreadyMonitoringEmails = new ArrayList<>();
		
		List<String> invalidPros   = new ArrayList<>();
		
		List<String> invalidEmails = new ArrayList<>();
		
		for(String emailAddr: emailAddressList) {
			
			for(String pro: statusReq.getPros()) {
				
				ServiceResponse response = emailStatusDAO.writeRequest(statusReq.getSession(), pro, emailAddr);
				
				if(response==null){
					
					if(! proNumbers.contains(pro)){
						proNumbers.add(pro);
					}
					
					if(! emails.contains(emailAddr)){
						emails.add(emailAddr);
					}
					
				}else if(response.getErrorCode().equals("STE0001")){
					
					if(! alreadyMonitoringPros.contains(pro)){
						alreadyMonitoringPros.add(pro);
					}
					
					if(! alreadyMonitoringEmails.contains(emailAddr)){
						alreadyMonitoringEmails.add(emailAddr);
					}
				}else if (response.getErrorCode().startsWith("EMR")){
					if(! invalidEmails.contains(emailAddr)){
						invalidEmails.add(emailAddr);
					}
				}else if (response.getErrorCode().equals("VAE1003")){
					if(! invalidPros.contains(pro)){
						invalidPros.add(pro);
					}
				}else{
					response.setMessage(errorDAO.getErrorMessage(response.getErrorCode()));
					responses.add(response);
				}
				
			}	
		}
		
		if(invalidEmails.size()>0){
			/**
			 * Response for Invalid Email Address 
			 */
			StringBuilder error = new StringBuilder();
			error.append("You entered invalid email address");
			if(invalidEmails.size()==1){
				error.append(" ");
			}else{
				error.append("es ");
			}
			error.append(": ");
			error.append(String.join(",",invalidEmails));
			
			responses.add(new ServiceResponse("EMR0001",error.toString()));
		}
		
		if(invalidPros.size()>0){
			/**
			 * Response for Invalid Email Address 
			 */
			StringBuilder error = new StringBuilder();
			error.append("You entered invalid PRO");
			if(invalidPros.size()==1){
				error.append(" ");
			}else{
				error.append("s ");
			}
			error.append(": ");
			error.append(String.join(",",invalidPros));
			
			responses.add(new ServiceResponse("VAE1003",error.toString()));
		}
		
		
		
		if(proNumbers.size()>0){
			
			/**
			 * Success Response combined for all PROs and Email Addresses
			 */
			
			StringBuilder successMessage = new StringBuilder();
			successMessage.append("Status updates for PRO");
			
			if(proNumbers.size() ==1){
				successMessage.append(" ");
			}else{
				successMessage.append("s ");
			}
			
			successMessage.append(String.join(", ", proNumbers));
			
			successMessage.append(" will be emailed to the following email address");
			
			if(emails.size() ==1){
				successMessage.append(" ");
			}else{
				successMessage.append("es ");
			}
			
			successMessage.append("until the shipment");
			
			if(proNumbers.size() ==1){
				successMessage.append(" is ");
			}else{
				successMessage.append("s are ");
			}
			
			successMessage.append("delivered: ");
			
			successMessage.append(String.join(", ", emails));
			responses.add(new ServiceResponse("",successMessage.toString()));
		}
		
		if(alreadyMonitoringPros.size()>0){
			
			/**
			 * Error Response for already monitoring. combined for all PROs and Email Addresses
			 */
			
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("The following PRO");
			
			if(alreadyMonitoringPros.size() ==1){
				errorMessage.append(" ");
			}else{
				errorMessage.append("s ");
			}
			
			errorMessage.append(String.join(", ", alreadyMonitoringPros));
			
			if(alreadyMonitoringPros.size() ==1){
				errorMessage.append(" is ");
			}else{
				errorMessage.append(" are ");
			}
			
			errorMessage.append(" already being monitored by the following email address");
			
			if(alreadyMonitoringEmails.size() ==1){
				errorMessage.append(" ");
			}else{
				errorMessage.append("es ");
			}
			errorMessage.append(": ");
			errorMessage.append(String.join(", ", alreadyMonitoringEmails));
			responses.add(new ServiceResponse("STE0001",errorMessage.toString()));
		}

		return responses;
	}

	private String[] splitEmailAddresses(String addresses)
	{
		return addresses.trim().split("\\s+");
	}
}
