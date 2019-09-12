/**
 * @author: Todd Allen
 *
 * Creation date: 03/27/2019
 */

package com.estes.myestes.rating.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.estes.myestes.rating.dto.Point;

/**
 * Rating utilities
 */
public class RatingUtil
{
	public static final int[] RATE_SERVICE_LEVELS_ORDER = {99, 95, 96, 98, 94, 93, 92, 97};
	
	public RatingUtil() {	}

	/**
	 * Get quote app value based on app types received in request
	 * 
	 * @param List of quote types selected
	 * @return App type
	 */
	public static String getApp(List<String> quoteApps)
	{
		if (quoteApps == null || quoteApps.isEmpty()) {
			return RatingConstants.LTL_QUOTE;
		}

		if (quoteApps.contains(RatingConstants.VTL_QUOTE)) {
			return RatingConstants.VTL_QUOTE;
		}
		else if (quoteApps.contains(RatingConstants.TIME_CRITICAL_QUOTE)) {
			return RatingConstants.TIME_CRITICAL_QUOTE;
		}

		return RatingConstants.LTL_QUOTE;
	} // getApp
	
	
	public static boolean isRateReturned(List<String> apps, int service)
	{
		boolean ltl = apps.contains(RatingConstants.LTL_QUOTE);
		boolean tc = apps.contains(RatingConstants.TIME_CRITICAL_QUOTE);
		boolean vtl = apps.contains(RatingConstants.VTL_QUOTE);

		// Never return "master" quote
		if (service == 0) {
			return false;
		}

		// Return all quotes if all quote types selected
		if (vtl && tc && ltl) {
			return true;
		}

		// Return all but LTL Standard if VTL, TC selected
		if (vtl && tc) {
			if (service < 99) {
				return true;
			}
		}

		// Return all but Guaranteed Exclusive Use if VTL and LTL selected
		if (vtl && ltl) {
			if (service != 97) {
				return true;
			}
		}

		// Return all but Volume and Truckload Basic if TC and LTL selected
		if (tc && ltl) {
			if (service != 94) {
				return true;
			}
		}

		// VTL-only quotes return service types 92, 93 and 94
		if (vtl) {
			if (service >= 92 && service <= 94) {
				return true;
			}
		}

		// TC-only quotes return service types 92, 93, 95, 96, 97 and 98
		if (tc) {
			if (service >= 92 && service < 99 && service != 94) {
				return true;
			}
		}

		// LTL-only quotes return service types 95, 96, 98 and 99
		if (ltl) {
			if (service >= 95 && service <= 99 && service != 97) {
				return true;
			}
		}

		return false;
	} // isRateReturned


	/**
	 * Determine whether rate quote is guaranteed
	 * 
	 * @param level Service level ID
	 * @return Indicator of guaranteed service
	 */
	public static boolean isGuaranteed(int level)
	{
		if (level != 94 && level < 99) {
			return true;
		}

		return false;
	} // isGuaranteed

	/**
	 * Test for Mexico point country
	 * 
	 * @param pt Estes point to check
	 * @return Indicator whether point country is Mexico
	 */
	public static boolean isMexico(Point pt)
	{
		return isMexico(pt.getCountry());
	} // isMexico

	/**
	 * Test for Mexico point country
	 * 
	 * @param country Country to check
	 * @return Indicator whether point country is Mexico
	 */
	public static boolean isMexico(String country)
	{
		if (!StringUtils.isEmpty(country) && country.equals("MX")) {
			return true;
		}

		return false;
	} // isMexico
	
	/**
	 * Get AppId by service Level Id
	 * @param serviceLevel
	 * @return appId [VTL,TC,LTL]
	 */
	public static String getAppIdByServiceLevel(int serviceLevel) {

		List<Integer> timeCriticalServiceLevels = Arrays.asList(92,93,95,96,97,98);
		
		
		if (serviceLevel==94) {
			
			return RatingConstants.VTL_QUOTE;
			
		} else if (timeCriticalServiceLevels.contains(serviceLevel)) {
			
			return RatingConstants.TIME_CRITICAL_QUOTE;
			
		} else if (serviceLevel==99) {
			
			return RatingConstants.LTL_QUOTE;
			
		} else {
			
			return RatingConstants.TIME_CRITICAL_QUOTE;
			
		}
		
	}

	public static <T> List<T> sortRateQuote(Map<Integer, T> results) {
		List<T> list = new ArrayList<>();
		for(int order: RATE_SERVICE_LEVELS_ORDER){
			if(results.containsKey(order)){
				list.add(results.get(order));
			}
		}
		return list;
	}
}
