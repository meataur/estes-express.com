
package com.estes.myestes.pcraterdownload.utils;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class PCRaterDownloadUtil {
	
	public static final String VALID_EMAIL_REGEX = ".+@.+\\.[a-zA-Z]++";

	public PCRaterDownloadUtil() {	}

	public static boolean isNullOrEmpty(String reqString) {
		if(reqString.equals(null) || reqString.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isNullOrEmpty(List<String> reqString) {
		if(CollectionUtils.isEmpty(reqString)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isValidEmail(String email){
		return email.matches(VALID_EMAIL_REGEX);
	}
}
