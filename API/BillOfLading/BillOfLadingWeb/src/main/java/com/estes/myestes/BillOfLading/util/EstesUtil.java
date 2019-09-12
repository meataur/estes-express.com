package com.estes.myestes.BillOfLading.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class EstesUtil {
	public static final String DATE_YYYYMMDD = "yyyyMMdd";
	public static final String MMDDYYYY = "MMddyyyy";
	public static String US_DATE_FORMAT   ="MM/dd/yyyy";
	public static String DATE_YYYY_MM_DD   ="yyyy-MM-dd";
	public static String US_DATE_FORMAT_WITHOUT_SLASH ="MMddyyyy";

	/**
	 * Get Date object into String format in given pattern
	 * @param date Date
	 * @param pattern String
	 * @return String
	 */
	public static String formatDate(Date date, String pattern){
		if(date==null){
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	

	
	
	/**
	 * Get Date object from String date with a specific pattern into Date type object
	 * @param date String
	 * @param pattern String [yyyyMMdd]
	 * @return Date
	 */
	public static Date formatDate(String date, String pattern){
		try{

			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
			return  dateFormat.parse(date); 
		}catch(ParseException ex){
			return null;
		}
	}
	
	/**
	 * Get Date object from String date with a specific pattern into Date type object
	 * @param date String
	 * @return Date
	 */
	public static Date formatDate(String date){
		
		
		if(date==null){
			return null;
		}
		
		if(date.replaceAll("\\D+", "").length()!=8){
			return null;
		}
		
		
		if(date.trim().length()==8 && date.replaceAll("\\D+", "").length()==8){
			return formatDate(date,DATE_YYYYMMDD); 
			
		}
		
		if(date.split("(/|-)").length==3){
			
			String[] dateParts = date.split("(/|-)");
			
			if(dateParts[0].length()==2){
				return formatDate(date.replaceAll("\\D+", ""),"MMddyyyy");
			}else if(dateParts[0].length()==4){
				return formatDate(date.replaceAll("\\D+", ""),DATE_YYYYMMDD);
			}
		}
		
		return null;
		
	}
	
	
	
	/**
	 * Get Digit from a string. It just removes the non-digit characters.
	 * It is useful to remove non-digit characters from Phone, FAX etc.
	 * @param str String
	 * @return String
	 */
	public static String removeNonDigit(String str){
		return str==null? "" : str.replaceAll("\\D+", "");
		
	}
	
	/**
	 * Get us phone or fax format (xxx) xxx xxxx from digit only phone or fax
	 * @param str String
	 * @return String
	 */
	public static String getPhoneOrFax(String str){
		
		return ( str!=null && str.length()>=10 ) ? "(" +str.substring(0, 3)+") "+ str.substring(3, 6)+" "+str.substring(6) : "";
		
	}
	
	
	public static String getTimestamp(){
		return (new Timestamp((new Date()).getTime())).toString().replaceAll(" ", "-").replaceAll(":", ".");
	}
	
	
	public static String formatTime(LocalTime localTime,String pattern){
		if(localTime==null){
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localTime.format(formatter);
	}
	
	
}

