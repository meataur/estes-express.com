package com.estes.myestes.transittimecalculator.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Estes TransitTime DTO 
 */
@ApiModel(description="Estes Transit Time Object")
public class TransitTime {

	@ApiModelProperty(notes = "Origin Point")
	Point originpoint;
	
	@ApiModelProperty(notes = "destination Points")
	List<DestinationPoint> destinationPoints;
	
	
	public TransitTime()
	{
		
	}

	public TransitTime(Point originpoint, List<DestinationPoint> destinationPoints) {
		super();
		this.originpoint = originpoint;
		this.destinationPoints = destinationPoints;
	}

	/**
	 * @return the originpoint
	 */
	public Point getOriginpoint() {
		return originpoint;
	}

	/**
	 * @param originpoint the originpoint to set
	 */
	public void setOriginpoint(Point originpoint) {
		this.originpoint = originpoint;
	}

	/**
	 * @return the destinationPoints
	 */
	public List<DestinationPoint> getDestinationPoints() {
		return destinationPoints;
	}

	/**
	 * @param destinationPoints the destinationPoints to set
	 */
	public void setDestinationPoints(List<DestinationPoint> destinationPoints) {
		this.destinationPoints = destinationPoints;
	}
	
	

}
