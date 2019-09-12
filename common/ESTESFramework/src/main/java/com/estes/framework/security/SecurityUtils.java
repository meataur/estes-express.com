/**
 * 
 */
package com.estes.framework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.estes.framework.logger.ESTESLogger;

/**
 * @author sinhasu
 * 
 */
public class SecurityUtils {

	private static final String USER_NAME = "  [NAME: ";
	private static final String USER_ID = " USERID: ";
	private static final String CLOSE_BRACKET = "]";
	private static final String ACCESS_DENIED = "Access Denied";

	public static EstesUser getUser() {
		EstesUser userObject = null;
		if (getAuthentication() != null) {
			userObject = (EstesUser) getAuthentication().getPrincipal();
		}
		return userObject;
	}

	public static String getUserDisplayName() {
		String displayName = null;
		EstesUser userObject = getUser();
		if (userObject != null) {
			displayName = userObject.getDisplayName();
		}
		return displayName;
	}

	public static String getUserName() {
		String userName = null;
		EstesUser userObject = getUser();
		if (userObject != null) {
			userName = userObject.getUsername();
		}
		return userName;
	}

	public static final String getUserInfo() {
		EstesUser userObject = getUser();
		StringBuilder sb = new StringBuilder();
		sb.append(USER_NAME).append(userObject.getUsername()).append(CLOSE_BRACKET);
		return sb.toString();
	}

	public static final String getUserInfoLog() {
		EstesUser userObject = getUser();
		StringBuilder sb = new StringBuilder();
		sb.append(USER_ID).append(userObject.getUsername());
		return sb.toString();
	}

	public static Authentication getAuthentication() {
		Authentication authentication = null;
		if (SecurityContextHolder.getContext() != null) {
			authentication = SecurityContextHolder.getContext().getAuthentication();
		}

		if (authentication == null) {
			ESTESLogger.log(ESTESLogger.ERROR, SecurityUtils.class, "getAuthentication()",
					"** AUTHENTICATION object is found NULL in SpringSecurityUtils.getAuthentication() method **");
		}
		return authentication;
	}
}
