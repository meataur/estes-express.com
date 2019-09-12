package com.estes.myestes.fuelsurcharge.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date Utils
 */
public class DateUtils
{

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	/**
	 * Get date from 'x' years ago from today in yyyyMMdd format
	 * @param subtractedYears
	 * @return
	 */
	public static String getDateFromYearsAgo(int yearsAgo) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -yearsAgo);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
	
}