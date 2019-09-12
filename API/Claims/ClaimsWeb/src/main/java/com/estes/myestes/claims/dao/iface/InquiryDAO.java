package com.estes.myestes.claims.dao.iface;

import java.util.List;

import com.estes.myestes.claims.dto.ClaimDTO;
import com.estes.myestes.claims.dto.ClaimsRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;

public interface InquiryDAO extends BaseDAO {
	List<ClaimDTO> getClaimsByDate(ClaimsRequestDTO request);
	List<ClaimDTO> getClaimsByProNumber(String proNumber, String account) throws ClaimsException;
	List<ClaimDTO> getClaimsByRefNumber(String refNumber, String account) throws ClaimsException;
	List<ClaimDTO> getClaimsByClaimNumber(String claimNumber, String account) throws ClaimsException;
}