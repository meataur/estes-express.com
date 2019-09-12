/**
 * @author: Todd Allen
 *
 * Creation date: 01/22/2019
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dao.iface.EmailDAO;
import com.estes.myestes.invoiceinquiry.dto.AgingEmailRequest;
import com.estes.myestes.invoiceinquiry.exception.AppException;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.EmailService;

@Service("emailService")
@Scope("prototype")
public class EmailServiceImpl implements EmailService
{
	@Autowired
	private EmailDAO emailDAO;

	/**
	 * Build string from the list of PROs.
	 * <br />
	 * The list must be padded due to the way CL programs handle lengthy strings.
	 * 
	 * @param pros PRO list
	 * @return Comma-separated string of PROs padded to 500 characters
	 */
	private String buildProString(List<String> proList)
	{
		// Put list of PROs (each PRO surrounded in double quotes) in to 1 string for SPROC call
		StringBuilder pros = new StringBuilder();
		for (String pro: proList) {
			pros.append("\"" + pro + "\"" + ",");
		}

		String proString = pros.toString();
		// Remove trailing comma
		if (!StringUtils.isEmpty(pros.toString())) {
			proString = pros.toString().substring(0, pros.toString().length()-1);
		}
		// Pad string of PROs to SPROC parameter string length
		return StringUtils.rightPad(proString, 500, "~");
	} // buildProString

	@Override
	public ServiceResponse sendAging(Account acct, AgingEmailRequest emailRequest) throws InvoiceException
	{
		
//		if(emailRequest.getPros().size()==0){
//			throw new AppException("error","Please select at least one PRO");
//		}
		
		// Put list of PROs in to 1 string for SPROC call
		String pros = buildProString(emailRequest.getPros());
		
		
		
		for (String address: emailRequest.getEmailAddresses()) {
			emailDAO.sendEmail(acct, StringUtils.rightPad(address, address.length(), ","), emailRequest.getFileFormat(),
					emailRequest.getBucket(), pros);
		}

		return new ServiceResponse("", "Your request has been processed. You should receive an email shortly.");
	} // sendAging
}
