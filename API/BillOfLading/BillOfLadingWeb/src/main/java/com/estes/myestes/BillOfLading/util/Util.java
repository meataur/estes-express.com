package com.estes.myestes.BillOfLading.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.util.StringUtils;

import com.estes.myestes.BillOfLading.util.MessageConstants;
import com.estes.myestes.BillOfLading.util.PickupRequestConstants;

/**
 * Various useful methods.
 * 
 * @author geudeka
 */
public class Util {

	private static Random random = new Random();
	
	public static String getExceptionMessage(String pExceptionMessage, long pErrorToken) {
		StringBuilder sb = new StringBuilder(MessageConstants.TOKEN);
		sb.append(pErrorToken);
		sb.append(PickupRequestConstants.COMMA_SEPARATOR).append(pExceptionMessage);
		return sb.toString();
	}
	
	public static String getFailureMessage(long pErrorToken) {
		String errorMessage = null;
		if (pErrorToken > 0) {
			errorMessage = MessageConstants.PICKUP_ACTION_FAILED_WITH_TOKEN + pErrorToken;
		} else {
			errorMessage = MessageConstants.PICKUP_ACTION_FAILED;
		}
		return errorMessage;
	}
	
	public static long getUniqueToken() {
		Calendar calendar = Calendar.getInstance();
		long tokenValue = calendar.getTimeInMillis();
		int randomValue = random.nextInt(100000);
		return (tokenValue + randomValue);
	}
	
	/** 
	 * Returns true if the String contains only digits; false otherwise.
	 */
	public static boolean containsOnlyDigits(String string) {
		return string.matches("\\d+");
	}
	
	/**
	 * Returns true if the value represents a positive decimal number.
	 */
	public static boolean isPositiveDecimal(String value) {
		double decimalValue = -1;
		
		try {
			decimalValue = Double.parseDouble(value);
		} catch(NumberFormatException e) {
			return false;
		}
		
		if(decimalValue < 0) { return false; }
		
		return true;
	}
	
	/** 
	 * Returns only the digits in the String.
	 */
	public static String getDigits(String string) {
		return string.replaceAll("\\D+", "");
	}
	
	/** 
	 * Returns true if the String loosely validates as an email address.
	 */
	public static boolean isValidEmail(String string) {
		if(string.length() > 254) { return false; } //maximum length
		return string.matches(".+@.+\\..+");
	}
	
	public static String getStringForDate(Date date){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(date);
	}
	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormattedDate(Date date, String format){
		if(format == null){
			format = "MM/dd/yyyy";
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * Convert hours and minutes into float variable for time comparisons
	 */
	public static Float getTimeToken(String hours, String minutes, String ampm){
		int tHours = 0;
		int tMinutes = 0;
		Float timeToken = 0f;
		if (StringUtils.hasText(minutes)) {
			tMinutes = Integer.parseInt(minutes);
		} 
		if (StringUtils.hasText(hours)) {
			tHours = Integer.parseInt(hours);
		} 
		if (StringUtils.hasText(ampm)) {
			String ampmString = ampm.trim().toUpperCase();
			if (ampmString.equals("PM")) {
				if (tHours != 12) {
					tHours = tHours + 12;
				} 
			}else if (ampmString.equals("AM")) {
				if (tHours == 12) {
					tHours = tHours - 12;
				} 
			}
		}
		timeToken = new Float(tHours+(tMinutes/100.0)); 
		return timeToken;
	}
	
	/**
	 * 
	 * @param hours
	 * @param minutes
	 * @param ampm
	 * @return
	 */
	public static Time getFormattedTime(String hours, String minutes, String ampm){
		Time formatTime = null;
		try {
			if(hours != null && minutes != null){
				int tHours = 0;
				if (StringUtils.hasText(hours)) {
					tHours = Integer.parseInt(hours);
				} 
				DateFormat sdf = new SimpleDateFormat("hh:mm");
				if (ampm.equals("PM") && tHours != 12) {
					tHours = tHours + 12;
				}
				String time1 = tHours + ":" +minutes;
				Date date = sdf.parse(time1);
				formatTime = new Time(date.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatTime;
	}
	
	public static Date getHHMMFormattedDate(String hours, String minutes, String ampm){
		Date formattedDate = null;
		try{
			if(hours != null && minutes != null){
				int tHours = 0;
				if (StringUtils.hasText(hours)) {
					tHours = Integer.parseInt(hours);
				} 
				DateFormat sdf = new SimpleDateFormat("hh:mm");
				if (ampm.equals("PM")) {
					tHours = tHours + 12;
				}
				String time1 = tHours + ":" +minutes;
				formattedDate = sdf.parse(time1);
			}
		}catch(ParseException pe){
			pe.printStackTrace();
		}
		return formattedDate;
	}
	/**
	 * Creates an executable SQL string for a call to a stored procedure.
	 * 
	 * @param schema name of the schema where the stored procedure lives
	 * @param programName name of the stored procedure
	 * @param parameters map of parameter names to their corresponding values
	 * @param parmNames array of Enums of all the same type, which is used to retrieve the corresponding name for each parameter
	 * and the order by which each parameter appears in the procedure call
	 */
	public static String callToString(String schemaName, String procedureName, Map<String, Object> parameters, Enum<?>[] parmNames) {
		
		StringBuilder sb = new StringBuilder("CALL ").append(schemaName).append('.').append(procedureName).append('(');
		
		for(Enum<?> parmName: parmNames) {
			sb.append("'").append(parameters.get(parmName.name())).append("', ");
		}
		
		return sb.substring(0, sb.lastIndexOf(", ")) + ")";
	}
	
	/**
	 * 
	 * @param pValue
	 * @return
	 */
	public static String trim(String pValue) {
		String value = pValue;
		if (pValue != null) {
			value = pValue.trim();
		}
		return value;
	}
	
	
	/**
	 * Creates an executable SQL string for a query to the database.
	 * 
	 * @param query the SQL parameterized query string
	 * @param args the parameter arguments for the query string
	 */
	public static String toStringQuery(String query, Object... args) {

		String[] sections = query.split("\\?");
		StringBuilder builder = new StringBuilder(sections[0]);
		 
		for(int i = 1; i < sections.length; ++ i) {
			
			Object parm = args[i - 1];
			
			if(parm instanceof String) {
				builder.append("'").append(parm).append("'").append(sections[i]);
			} else {
				builder.append(parm).append(sections[i]);
			}	
		}
		
		if(query.charAt(query.length() - 1) == '?') {
			
			Object parm = args[args.length - 1];
			
			if(parm instanceof String) {
				builder.append("'").append(parm).append("'");
			} else {
				builder.append(parm);
			}
		}
	
		return builder.toString();
	}
	

	
	
	/**
	 * returns current timestamp
	 * @return
	 */
	public static Timestamp getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * returns current date
	 * @return
	 */
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	/**
	 * returns current date
	 * @return
	 */
	public static Date getCurrentDateNoOffSet(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * format date but time portion set to zeros
	 */
	public static Date getTruncatedDate(long pDateInMillis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(pDateInMillis);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
}