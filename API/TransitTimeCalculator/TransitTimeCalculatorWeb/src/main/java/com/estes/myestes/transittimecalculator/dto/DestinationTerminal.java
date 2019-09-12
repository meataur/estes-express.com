package com.estes.myestes.transittimecalculator.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes DestinationTerminal DTO
 * @author chandye
 *
 */

public class DestinationTerminal {
	
	@ApiModelProperty(notes="Destination Terminal")
	Terminal destinationTerminal;

	@ApiModelProperty(notes="Service Days")
	String serviceDays;

	@ApiModelProperty(notes="Informational Message")
	String message;
	
	/**
	 * @return the destinationTerminal
	 */
	public Terminal getDestinationTerminal() {
		return destinationTerminal;
	}

	/**
	 * @param destinationTerminal the destinationTerminal to set
	 */
	public void setDestinationTerminal(Terminal destinationTerminal) {
		this.destinationTerminal = destinationTerminal;
	}

	/**
	 * @return the serviceDays
	 */
	public String getServiceDays() {
		return serviceDays;
	}
	
	public DestinationTerminal()
	{
		
	}

	public DestinationTerminal(Terminal destinationTerminal, String serviceDays) {
		super();
		this.destinationTerminal = destinationTerminal;
		this.serviceDays = serviceDays;
	}

	/**
	 * @param serviceDays the serviceDays to set
	 */
	public void setServiceDays(String serviceDays) {
		this.serviceDays = serviceDays;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


}
