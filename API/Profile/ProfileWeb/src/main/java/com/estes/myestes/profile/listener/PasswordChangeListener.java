package com.estes.myestes.profile.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.profile.event.PasswordChangeEvent;

@Component
public class PasswordChangeListener implements ApplicationListener<PasswordChangeEvent>{
	/**
	 * Pass change event listener.
	 * This will be used if we need to send any email/sms password changing notification
	 */
	@Override
	public void onApplicationEvent(PasswordChangeEvent event) {
		final String username = event.getUsername();
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "PasswordChangeEvent()", "Password for "+username+" has been changed!");
	}

}
