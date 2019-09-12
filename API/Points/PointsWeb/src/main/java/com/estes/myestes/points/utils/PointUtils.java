package com.estes.myestes.points.utils;

import org.springframework.util.StringUtils;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dto.Point;

public class PointUtils
{
	/**
	 * Test for error in response
	 * 
	 * @param resp Response to check
	 * @return Indicator whether terminal has an error
	 */
	public static boolean hasError(ServiceResponse resp)
	{
		if (!StringUtils.isEmpty(resp.getMessage())) {
			return true;
		}

		return false;
	} // hasError

	/**
	 * Test point for emptiness
	 * 
	 * @param pt Point to check
	 * @return Indicator whether point is empty
	 */
	public static boolean isEmpty(Point pt)
	{
		if (StringUtils.isEmpty(pt.getCountry()) && StringUtils.isEmpty(pt.getZip()) && StringUtils.isEmpty(pt.getCity())
				&& StringUtils.isEmpty(pt.getState())) {
			return true;
		}

		return false;
	} // isEmpty

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
		if (!StringUtils.isEmpty(country) && country.equals(PointConstants.MEXICO)) {
			return true;
		}

		return false;
	} // isMexico
	
	/**
	 * Pad string on right.
	 * 
	 * @param  elem String to be padded
	 * @param  pad  String to use for padding
	 * @param  len  Length of padded string
	 * @return String with character padded to the right
	 */
	public static String padRight(String str, String pad, int len)	{
		if (str == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(str);
		for (int i=str.length(); i<len; i++) {
			buf.append(pad);
		}

		return buf.toString();
    } 
}
