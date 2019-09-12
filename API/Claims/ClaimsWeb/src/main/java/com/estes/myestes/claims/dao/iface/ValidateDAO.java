package com.estes.myestes.claims.dao.iface;

import com.estes.myestes.claims.exception.ClaimsException;

public interface ValidateDAO extends BaseDAO {
	Boolean validateProNumber(String number);
	Boolean validateReferenceNumber(String account, String number);
	Boolean validateClaimNumber(String account, String number);
	Boolean validateAccount(String parentAccount, String subAccount, String accountType) throws ClaimsException;
}