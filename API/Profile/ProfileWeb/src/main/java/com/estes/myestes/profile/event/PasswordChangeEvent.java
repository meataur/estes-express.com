package com.estes.myestes.profile.event;

import org.springframework.context.ApplicationEvent;

import com.estes.framework.logger.ESTESLogger;

import lombok.Getter;

@SuppressWarnings("serial")
public class PasswordChangeEvent extends ApplicationEvent {
	@Getter
	private final String username;
	public PasswordChangeEvent(final String username) {
		super(username);
		this.username = username;
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "PasswordChangeEvent()", "Password for "+username+" has been changed!");
	}

}
