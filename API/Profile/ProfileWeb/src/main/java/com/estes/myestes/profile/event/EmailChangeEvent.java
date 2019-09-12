package com.estes.myestes.profile.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.estes.framework.logger.ESTESLogger;

import lombok.Getter;

@SuppressWarnings("serial")
public class EmailChangeEvent extends ApplicationEvent {
	@Getter
	private final  Map<String, String> info;
	
	public EmailChangeEvent(final Map<String, String> info) {
		super(info);
		this.info = info;
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "EmailChangeEvent()", "Email for "+info.get("username")+" has been changed!");
	}

}
