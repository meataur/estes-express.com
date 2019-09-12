/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 */
package com.estes.myestes.recoverpassword.utils;

import org.springframework.util.StringUtils;
import com.estes.dto.common.ServiceResponse;

public class RecoverPasswordUtils
{
	/**
	 * Test for error in response
	 * 
	 * @param resp Response to check
	 * @return Indicator whether Send email has an error
	 */
	public static boolean hasError(ServiceResponse resp)
	{
		if (!StringUtils.isEmpty(resp.getMessage())) {
			return true;
		}

		return false;
	} // hasError
	
	/**
	 * returns true if the given String follows the format of an email address.
	 * @param email
	 * @return
	 */
	public static boolean validEmail(String email){
		String[] emailSplitOnAt = email.split("@");
		if( !(email.indexOf("\"") >= 0) && !(email.indexOf("'") >= 0) ){
			if(emailSplitOnAt.length == 2){
				if(emailSplitOnAt[1].indexOf('.') > 0){
					if(emailSplitOnAt[1].indexOf('.') < (emailSplitOnAt[1].length() -1)){
						if(emailSplitOnAt[0].length() > 0){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
