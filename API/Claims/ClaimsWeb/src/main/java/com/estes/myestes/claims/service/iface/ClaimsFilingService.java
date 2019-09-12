/**
 *
 */

package com.estes.myestes.claims.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.claims.dto.ClaimantResponseDTO;
import com.estes.myestes.claims.dto.OtProRequestDTO;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;


public interface ClaimsFilingService {

	/**
	 * Submit request to get pro information
	 * 
	 * @return pro information
	 * @throws ClaimsException 
	 */
	public ProInfoResponseDTO getProInfo(OtProRequestDTO request) throws ClaimsException;
	
	public ClaimantResponseDTO getClaimantInfo(String username) throws ClaimsException;
	
	public boolean isPartyToShipment(String account, String accountType, String otPro) throws ClaimsException;
	
	public boolean isL2LShipment(String otPro) throws ClaimsException;

	public ServiceResponse fileClaim(String username, SubmitClaimRequestDTO claim) throws ClaimsException;
	
	public boolean hasEnteredClaim(String otPro) throws ClaimsException;
}