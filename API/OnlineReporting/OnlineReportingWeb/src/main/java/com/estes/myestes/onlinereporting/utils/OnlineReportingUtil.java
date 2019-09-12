/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.edps.format.DateFormat;
import com.edps.format.StringFormat;

public class OnlineReportingUtil
{
	public static final String VALID_EMAIL_REGEX = ".+@.+\\.[a-zA-Z]++";
	
	public OnlineReportingUtil() {	}

	public static String formatDate(String date){
		return DateFormat.formatToUsaDate(date.replaceAll(DateFormat.SQL_DATE_SEPARATOR, ""));
	}
	
	public static String getDateWeeksFromToday(String weeks) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, StringFormat.toInt(weeks));
		java.util.Date date = calendar.getTime();
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}
	
	public static String getDateMonthsFromToday(String months) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.MONTH, StringFormat.toInt(months));
		java.util.Date date = calendar.getTime();
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}
	
	public static boolean isNullOrEmpty(String reqString) {
		if(reqString.equals(null) || reqString.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isValidEmail(String email){
		return email.matches(VALID_EMAIL_REGEX);
	}
	
	public static String removeDashes(String s){
		return s.replaceAll("-", "");
	}
	
	public static boolean dateExpired(String date){
		String currentDate = DateFormat.getCurrentDateYYYYMMDD();
		if(StringFormat.toInt(date) < StringFormat.toInt(currentDate)){
			return true;
		}
		return false;
	}
	
	 public static String convert8digitNumericToDateString(String unformattedDate, String separator) {
		StringBuffer formatted = new StringBuffer();
		
		formatted.append(unformattedDate.charAt(4));
		formatted.append(unformattedDate.charAt(5));
		formatted.append(unformattedDate.charAt(6));
		formatted.append(unformattedDate.charAt(7));
		formatted.append(separator);
		formatted.append(unformattedDate.charAt(0));
		formatted.append(unformattedDate.charAt(1));
		formatted.append(separator);
		formatted.append(unformattedDate.charAt(2));
		formatted.append(unformattedDate.charAt(3));
		
		return formatted.toString();
		 
	}
}
