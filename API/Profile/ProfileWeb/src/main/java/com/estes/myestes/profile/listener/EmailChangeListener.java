package com.estes.myestes.profile.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.profile.event.EmailChangeEvent;

@Component
public class EmailChangeListener implements ApplicationListener<EmailChangeEvent>{
	/**
	 * Email change event listener.
	 * This will be used if we need to send any email/sms  notification
	 */
	@Override
	public void onApplicationEvent(EmailChangeEvent event) {
		final String username = event.getInfo().get("username");
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "EmailChangeEvent()", "Email for "+username+" has been changed!");
	}

}