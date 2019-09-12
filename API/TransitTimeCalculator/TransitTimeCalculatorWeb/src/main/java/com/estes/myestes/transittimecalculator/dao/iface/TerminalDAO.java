/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
  */

package com.estes.myestes.transittimecalculator.dao.iface;

import com.estes.myestes.transittimecalculator.dto.TransitTime;
import com.estes.myestes.transittimecalculator.dto.TransitTimeResponse;
import com.estes.myestes.transittimecalculator.exception.TransitTimeCalculatorException;

public interface TerminalDAO
{
	/**
	 * Calculate the transit time between Origin and destination
	 * 
	 * @param transitTime TransitTime 
	 */
	public TransitTimeResponse calculateTransitTime(TransitTime transitTime) throws TransitTimeCalculatorException;

	
}
