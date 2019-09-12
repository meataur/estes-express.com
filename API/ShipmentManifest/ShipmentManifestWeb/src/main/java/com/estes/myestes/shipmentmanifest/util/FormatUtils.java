package com.estes.myestes.shipmentmanifest.util;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shipmentmanifest.dao.impl.ManifestDAOImpl;

public class FormatUtils {
	
	/**
	 * Uses the SimpleDateFormat class to convert a time <code>String</code>
	 * from one format to another.
	 * @param time, the time String to format
	 * @param fromFormat original format of the time <code>String</code>, 
	 * @param toFormat resulting format of the time <code>String</code>,
	 * @return the formatted time <code>String</code>
	 */
	public static String formatTimeString(String time, String fromFormat, String toFormat) {
		if (StringUtils.isNotBlank(time)) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(fromFormat);
				format.setLenient(false);
				return new SimpleDateFormat(toFormat).format(format.parse(time));
			} catch(Exception e) {
				ESTESLogger.log(ESTESLogger.ERROR, ManifestDAOImpl.class, "formatTimeString", e.getMessage());
			}
		}
		return time;
	}
	
}