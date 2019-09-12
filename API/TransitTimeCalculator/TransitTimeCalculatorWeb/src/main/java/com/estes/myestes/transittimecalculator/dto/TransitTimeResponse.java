package com.estes.myestes.transittimecalculator.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes TransitTimeResponse DTO
 * @author chandye
 *
 */

public class TransitTimeResponse {
	
	@ApiModelProperty(notes="Origin Terminal")
	Terminal originTerminal;
	
	@ApiModelProperty(notes="Destination Terminals")
	List<DestinationTerminal> destinationTerminals;

	/**
	 * @return the originTerminal
	 */
	public Terminal getOriginTerminal() {
		return originTerminal;
	}

	/**
	 * @param originTerminal the originTerminal to set
	 */
	public void setOriginTerminal(Terminal originTerminal) {
		this.originTerminal = originTerminal;
	}

	/**
	 * @return the destinationTerminals
	 */
	public List<DestinationTerminal> getDestinationTerminals() {
		return destinationTerminals;
	}

	/**
	 * @param destinationTerminals the destinationTerminals to set
	 */
	public void setDestinationTerminals(List<DestinationTerminal> destinationTerminals) {
		this.destinationTerminals = destinationTerminals;
	}


	
}
