package com.estes.myestes.profile.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.estes.framework.logger.ESTESLogger;

import lombok.Getter;

@SuppressWarnings("serial")
public class UsernameChangeEvent extends ApplicationEvent {
	@Getter
	private final  Map<String, String> info;
	
	public UsernameChangeEvent(final Map<String, String> info) {
		super(info);
		this.info = info;
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "UsernameChangeEvent()", "Username for "+info.get("username")+" has been changed!");
	}

}
