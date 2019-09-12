/**
 * @author: Todd Allen
 *
 * Creation date: 02/20/2018
 */

package com.estes.services.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.TerminalDAO;
import com.estes.services.common.dto.Terminal;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.TerminalService;

/**
 * Estes terminal lookup implementation 
 */
@Service("terminalService")
@Scope("prototype")
public class TerminalServiceImpl implements TerminalService
{
	@Autowired
	private TerminalDAO terminalDAO;

	/**
	 * Create a new service
	 */
	public TerminalServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estesexpress.points.service.iface.TerminalService#search()
	 */
	@Override
	public Terminal getTerminal(int termId) throws ServiceException
	{
		return terminalDAO.getTerminalInfo(termId);
	} // getTerminal
}
