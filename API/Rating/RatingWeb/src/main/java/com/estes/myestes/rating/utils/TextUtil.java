/**
 * @author: Todd Allen
 *
 * Creation date: 03/22/2019
 */

package com.estes.myestes.rating.utils;

/**
 * Text utilities
 */
public class TextUtil
{
	public TextUtil() {	}

	/**
	 * Scrub string to remove HTML tags and carriage returns
	 * 
	 * @param text String to be scrubbed
	 * @return String with HTML tags removed
	 */
	public static String scrubHtml(String text)
	{
		String scrubbed = text.replaceAll("\\<.*?>","").replaceAll("\\r\\n"," ");

		// Check for stray opening HTML tags
		if (scrubbed.indexOf("<") >= 0) {
			return scrubbed.substring(0, scrubbed.indexOf("<"));
		}
		// Check for stray closing HTML tags
		else if (scrubbed.indexOf(">") >= 0) {
			return scrubbed.substring(scrubbed.indexOf(">")+1, scrubbed.length());
		}

		return scrubbed;
	} // scrubHtml
}
