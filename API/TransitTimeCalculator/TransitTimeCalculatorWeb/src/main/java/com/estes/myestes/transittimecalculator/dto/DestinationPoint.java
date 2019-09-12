/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes Destiantion point DTO
 */
@ApiModel(description="Estes Destination point")
public class DestinationPoint
{
	@ApiModelProperty(notes="Destination Point")
	Point  point;
	
    @ApiModelProperty(notes="Shipment Date – MM/DD/YYYY")
    String shipmentDate;

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * @return the shipmentDate
	 */
	public String getShipmentDate() {
		return shipmentDate;
	}

	/**
	 * @param shipmentDate the shipmentDate to set
	 */
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	
	


	}
