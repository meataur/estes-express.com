package com.estes.myestes.recoverpassword.utils;

/**
 * Constants
 */
public interface RecoverPasswordConstants
{
	public static final String APP_CONFIG = "recoverpassword.config";
	public static final String LOGGER = "logger";
	public static final String LOCATION = "location";
	public static final String MAIL_FROM = "password@estes-express.com";
	public static final String MAIL_SUBJECT = "My Estes username and password";
	public static final String APP_MAIL_JNDI = "mail/msrecoverpwdemail";
	public static final String EMAIL_ID = "email";
	public static final String MAIL_TO = "mailTo";
	public static final String INVALID_CRITERIA_ERROR = "You must enter either your e-mail address or username.";
	public static final String INVALID_EMAIL_ERROR = "Please enter a valid e-mail address.";
	public static final String NO_USER_PROFILE_ERROR = "There are no My Estes user profiles signed up matching this search criteria. If you have further questions, please <a href=/contact?type_of_help=Customer%20Account%20Information>contact us</a>.";
	public static final String EMAIL_SEND_ERROR = "Error sending email.";
	public static final String EMAIL_ON_FILE_ERROR = "The e-mail address on file is not valid.  Please [placeholder for RequestInfo app] click here to send us a valid e-mail address.";
	public static final String SUCCESS_MESSAGE = "Your username and password will be sent to userEmailAddress.";
	/*
	 * Default error message for ReST service calls
	 */
	String DEFAULT_ERROR = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
