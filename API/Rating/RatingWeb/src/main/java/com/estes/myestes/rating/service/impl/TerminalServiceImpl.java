/**
 * @author: Todd Allen
 * 
 * Cloned from points.service.impl#TerminalServiceImpl
 *
 * Creation date: 02/20/2018
 */

package com.estes.myestes.rating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.rating.dao.iface.TerminalDAO;
import com.estes.myestes.rating.dto.Point;
import com.estes.myestes.rating.dto.Terminal;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.TerminalService;
import com.estes.myestes.rating.utils.RatingUtil;

/**
 * Estes points lookup implementation 
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

	@Override
	public Terminal getTerminal(Point point) throws RatingException
	{
		if(RatingUtil.isMexico(point)){
			//point.setCity(String.format("%-" + 16 + "s", point.getCity()) + 
			//		String.format("%-" + 4 + "s", point.getState()));
//			point.setCity(PointUtils.padRight(point.getCity(), " ", 16) + 
//					PointUtils.padRight(point.getState(), " ", 4));
			point.setState("MX");
			point.setZip(point.getZip() + "M");
		}

		return terminalDAO.getTerminalInfo(point);
	} // getTerminal
}
