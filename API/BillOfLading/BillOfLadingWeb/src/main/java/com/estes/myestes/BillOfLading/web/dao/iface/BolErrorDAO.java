package com.estes.myestes.BillOfLading.web.dao.iface;

import com.estes.dto.common.ServiceResponse;

public interface BolErrorDAO
{
	/**
	 * Valid error code
	 */
	public static String VALID_ERROR_CODE = "0000000";

	/**
	 * Look up error message for error code
	 * 
	 * @param code Error code to look up
	 * @return  Error message associated with error code
	 */
	String getErrorMessage(String code);

	/**
	 * Determine presence of error by checking error code
	 * 
	 * @param code Error code to compare
	 * @return  Indication of error
	 */
	public static boolean isError(String code)
	{
		return !( code==null || "".equals(code.trim()) || code.trim().equals(VALID_ERROR_CODE));
	} // isError

	ServiceResponse getServiceResponse(Object object);
}