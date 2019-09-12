/**
 * @author: Todd Allen
 *
 * Creation date: 12/14/2017
 */

package com.estesexpress.currency.convert.service.iface;

import com.estesexpress.currency.convert.dto.RateDTO;
import com.estesexpress.currency.convert.service.exception.CurrencyConversionException;

/**
 * Currency rate service
 */
public interface RateService
{
	/**
	 * Get currency rates for country
	 * 
	 * @param  countryAbbrev Country currency abbreviation; e.g. CAD
	 * @return array of {@link RateDTO} objects
	 */
	public RateDTO[] getRates(String countryAbbrev) throws CurrencyConversionException;
}
