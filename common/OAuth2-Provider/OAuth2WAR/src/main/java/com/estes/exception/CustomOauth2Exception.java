package com.estes.exception;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;


@SuppressWarnings("serial")
public class CustomOauth2Exception extends ClientAuthenticationException {

	public CustomOauth2Exception(String msg, Throwable t) {
		super(msg, t);
	}

	public CustomOauth2Exception(String msg) {
		super(msg);
	}

	@Override
	public int getHttpErrorCode() {
		return 401;
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_request";
	}
}
