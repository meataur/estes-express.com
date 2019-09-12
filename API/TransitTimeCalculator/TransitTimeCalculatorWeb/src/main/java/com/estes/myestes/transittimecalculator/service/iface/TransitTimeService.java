/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.service.iface;

import com.estes.myestes.transittimecalculator.dto.TransitTime;
import com.estes.myestes.transittimecalculator.dto.TransitTimeResponse;
import com.estes.myestes.transittimecalculator.exception.TransitTimeCalculatorException;


/**
 * Servicing TransitTimeService
 */
public interface TransitTimeService
{
	public TransitTimeResponse calculateTransitTime(TransitTime transitTime) throws TransitTimeCalculatorException;
}
