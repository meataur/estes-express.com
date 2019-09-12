package com.estes.myestes.claims.dao.iface;

import com.estes.myestes.claims.dto.ClaimantResponseDTO;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.util.ClaimsFormFileItems;

public interface FilingDAO extends BaseDAO {
	ProInfoResponseDTO getProInfo(String ot, String pro) throws ClaimsException;
	ClaimantResponseDTO getClaimantInfo(String username) throws ClaimsException;
	boolean isPartyToShipment(String account, String accountType, String ot, String pro) throws ClaimsException;
	boolean isL2LShipment(String ot) throws ClaimsException;
	String accountTypeForCode(String accountCode) throws ClaimsException;
	boolean hasEnteredClaim(String ot, String pro) throws ClaimsException;
	String getClaimTypeNumber(String claimType, String otPro) throws ClaimsException;
	String writeHeader(String username, SubmitClaimRequestDTO claim, String claimTypeNumber) throws ClaimsException;
	void writeDetails(String claimsNumber, String claimTypeNumber, ClaimsFormFileItems fileItems) throws ClaimsException;
	String getCompanyName(String username) throws ClaimsException;
}