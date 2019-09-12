/**
 * @author: Todd Allen
 *
 * Creation date: 12/15/2017
 */

package com.estesexpress.currency.convert.dao.iface;

import com.estesexpress.currency.convert.dto.RateDTO;
import com.estesexpress.currency.convert.service.exception.CurrencyConversionException;

public interface RatesDAO
{
	/**
	 * Get currency exchanges rates from database
	 * 
	 * @param currency Currency code
	 */
	RateDTO[] getRates(String currency) throws CurrencyConversionException;
}
