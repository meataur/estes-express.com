/**
 * @author: Todd Allen
 *
 * Creation date: 04/11/2018
 */

package com.estes.myestes.accountrequest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edps.email.EMailMessage;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.accountrequest.dao.iface.BlockedEmailDAO;
import com.estes.myestes.accountrequest.dto.RequestInfo;
import com.estes.myestes.accountrequest.exception.AccountRequestException;
import com.estes.myestes.accountrequest.service.iface.AccountService;
import com.estes.myestes.accountrequest.utils.AppConstants;

/**
 * My Estes account code/number request handler service implementation
 */
@Service("acctService")
@Scope("prototype")
public class AccountServiceImpl implements AccountService
{
	@Autowired
	private BlockedEmailDAO emailDAO;

	/**
	 * Create a new service
	 */
	public AccountServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.accountrequest.service.iface.AccountService#requestNumber()
	 */
	@Override
	public ServiceResponse requestNumber(RequestInfo info) throws AccountRequestException
	{
		/*
		 *  Return generic error if basic validation failed.
		 *  This means UI was bypassed and we don't want to give specific errors.
		 */
		if (!validateRequest(info)) {
			return new ServiceResponse(AppConstants.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_APP_ERROR);
		}

		if (emailDAO.isBlocked(info.getEmail())) {
			return new ServiceResponse(AppConstants.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_APP_ERROR);
		}
		else {
			if (sendEmail(info)) {
				return new ServiceResponse("", AppConstants.SUCCESS_MESSAGE);
			}
			else {
				return new ServiceResponse(AppConstants.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_APP_ERROR);
			}
		}
	} // requestNumber

	/**
	 * Send account code/number e-mail request
	 * 
	 * @param reqInfo Requesting company info
	 * @return Boolean indicator of success
	 */
	private boolean sendEmail(RequestInfo reqInfo) throws AccountRequestException
	{
		String emailMessage = ""+
				"Name - " + 
				reqInfo.getName()+
				"\r\n"+
				"Requestor's Company Name - "+
				reqInfo.getCompany()+
				"\r\n"+
				"Requested Company's Name - "+
				reqInfo.getParentCompany()+
				"\r\n"+
				"Requestor's Phone Number - "+
				reqInfo.getPhone()+
				"\r\n"+
				"Addresses - "+
				"\r\n"+
				reqInfo.getAddresses()+
				"\r\n";

			String subject = "";
			if (reqInfo.getSource() != null && reqInfo.getSource().equals("rate")){
				subject = "PC Rater Download Account Number Request";
			}
			else {
				subject = "My Estes Signup Account Number Request";
			}

			try {
				EMailMessage email = new EMailMessage();
				email.setMailJndi(AppConstants.APP_MAIL_JNDI);
				email.setFrom(reqInfo.getEmail());
				email.addTo(ESTESConfigUtil.getProperty(AppConstants.MAIL, AppConstants.MAIL_TO));
				email.setMessageBody(emailMessage);
				email.setSubject(subject);
				return email.send();
			}
			catch (Exception e) {
				ESTESLogger.log(ESTESLogger.ERROR, AccountServiceImpl.class, "sendEmail()", "** Error occurred sending account request e-mail.", e);
	    		throw new AccountRequestException("Account number request e-mail failed.", e);
			}
	} // sendEmail

	/**
	 * Perform simple validation on request information
	 * 
	 * @param reqInfo Requesting company info
	 * @return Boolean indicator of valid request data
	 */
	private boolean validateRequest(RequestInfo req)
	{
		// Check all required fields for blank/null values
		if (StringUtils.isEmpty(req.getName()) || StringUtils.isEmpty(req.getCompany())
				|| StringUtils.isEmpty(req.getEmail()) || StringUtils.isEmpty(req.getPhone())
				|| StringUtils.isEmpty(req.getParentCompany()) || StringUtils.isEmpty(req.getAddresses())) {
			return false;
		}

		if (req.getPhone().length() < 10) {
			return false;
		}

		return true;
	} // validateRequest
}
