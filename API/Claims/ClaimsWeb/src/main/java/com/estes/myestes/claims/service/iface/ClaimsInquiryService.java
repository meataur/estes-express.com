/**
 *
 */

package com.estes.myestes.claims.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.claims.dto.ClaimDTO;
import com.estes.myestes.claims.dto.ClaimsRequestDTO;
import com.estes.myestes.claims.dto.EmailClaimsRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;


public interface ClaimsInquiryService {
	
	/**
	 * Check if valid account
	 * 
	 * @return claims
	 * @throws ClaimsException 
	 */
	public boolean validAccount(String parentAccount, String subAccount, String accountType) throws ClaimsException;

	/**
	 * Submit request to get claims
	 * 
	 * @return claims
	 * @throws ClaimsException 
	 */
	public List<ClaimDTO> getClaims(String account, String accountType, ClaimsRequestDTO request) throws ClaimsException;

	/**
	 * Submit request to email claims
	 * 
	 * @return boolean if email sent successfully
	 * @throws ClaimsException 
	 */
	public boolean emailClaims(String account, String accountType, EmailClaimsRequestDTO request) throws ClaimsException;
	
	/**
	 * get the error message associated with code
	 * 
	 * @return String of error message
	 * @throws ClaimsException 
	 */
	public String getErrorMessage(String errorCode);
}