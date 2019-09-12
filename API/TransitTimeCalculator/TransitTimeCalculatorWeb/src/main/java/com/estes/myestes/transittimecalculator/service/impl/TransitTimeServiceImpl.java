/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 *
 */

package com.estes.myestes.transittimecalculator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.transittimecalculator.dao.iface.TerminalDAO;
import com.estes.myestes.transittimecalculator.dto.TransitTime;
import com.estes.myestes.transittimecalculator.dto.TransitTimeResponse;
import com.estes.myestes.transittimecalculator.exception.TransitTimeCalculatorException;
import com.estes.myestes.transittimecalculator.service.iface.TransitTimeService;



/**
 * Servicing transitTimeCalculatorService lookup implementation
 */
@Service("transitTimeCalculatorService")
@Scope("prototype")
public class TransitTimeServiceImpl implements TransitTimeService{
	
	@Autowired
	private TerminalDAO terminalDAO;

	/**
	 * Create a new service
	 */
	public TransitTimeServiceImpl()
	{
		super();
	} // Constructor

	/* (non-Javadoc)
	 * @seecom.estes.myestes.points.service.iface.CoverageService#getTerminals(Point)
	 */
	@Override
	public TransitTimeResponse calculateTransitTime(TransitTime transitTime) throws TransitTimeCalculatorException
	{

		TransitTimeResponse transitTimeResponse = terminalDAO.calculateTransitTime(transitTime);

		return transitTimeResponse;
	} 
}
