package com.estes.myestes.requestinfo.util;

import java.io.Serializable;

/**
 * Types of problems on a <code>RequestForm</code>.
 */
public enum ProblemType implements Serializable {
	
	trackingHelp("Tracking Help", ""),
	
	imageNotAvailable("Image Not Available", 
		"This option is only available for parties to the shipment."),
		
	ratingQuestion("Rating Question", 
		"This option is only available for the payor of the shipment."),
		
	other("Other (Describe Below)", "");
	
	private boolean isHidden;
	private final String text, description;

	private ProblemType(String text, String description) {
		this.text = text;
		this.description = description;
	}
	
	public boolean isHidden() {
		return isHidden;
	}
	
	public String getText() {
		return text;
	}
	
	public String getDescription() {
		return description;
	}
}