/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.AppStatusDAO;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.AppService;

@Service("appService")
@Scope("prototype")
public class AppServiceImpl implements AppService
{
	@Autowired
	private AppStatusDAO appStatus;

	@Override
	public boolean getAvailability() throws ServiceException
	{
		return appStatus.isInvoiceInquiryAvailable();
	} // getAvailability

	@Override
	public boolean getAvailability(String app) throws ServiceException
	{
		return appStatus.isAppAvailable(app);
	} // getAvailability
}
