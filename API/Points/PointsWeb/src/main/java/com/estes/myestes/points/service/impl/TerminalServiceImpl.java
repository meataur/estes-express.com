/**
 * @author: Todd Allen
 *
 * Creation date: 02/20/2018
 */

package com.estes.myestes.points.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.points.dao.iface.TerminalDAO;
import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.TerminalService;
import com.estes.myestes.points.utils.PointUtils;

/**
 * Estes points lookup implementation 
 */
@Service("searchService")
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
	 * @see com.estes.myestes.points.service.iface.TerminalService#search()
	 */
	@Override
	public Terminal getTerminal(Point point) throws PointException
	{
		if(PointUtils.isMexico(point)){
//			if(point.getCity().contains(" MX ")){
//				point.setCity(point.getCity().substring(0,point.getCity().indexOf(" MX "))+"  MX");
//			}else{
//				point.setCity(PointUtils.padRight(point.getCity(), " ", 16) + 
//						PointUtils.padRight(point.getState(), " ", 4));
//			}
			
			point.setState("MX");
			point.setZip(point.getZip() + "M");
		}
		return terminalDAO.getTerminalInfo(point);
	} // getTerminal
}
