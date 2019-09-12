/**
 * @author: Todd Allen
 *
 * Creation date: 11/08/2018
 */

package com.estes.services.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.StateDAO;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.StateService;

/**
 * Estes state/province list implementation 
 */
@Service("stateService")
@Scope("prototype")
public class StateServiceImpl implements StateService
{
	@Autowired
	private StateDAO stateDAO;

	/**
	 * Create a new service
	 */
	public StateServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estes.services.common.service.iface.StateService#getStates()
	 */
	@Override
	public List<String> getStates(String ctry) throws ServiceException
	{
		return stateDAO.getStatesForCountry(ctry);
	} // getStates
}
