/**
 * @author: Todd Allen
 *
 * Creation date: 02/05/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Commodity information
 */
@ApiModel(description="Commodity information")
public class Commodity
{
	@ApiModelProperty(notes="position=1, Shipment class")
	double shipClass;
	@ApiModelProperty(notes="position=2, Total weight (lbs)")
	int weight;
	@ApiModelProperty(notes="position=3, Number of pieces")
	int pieces;
	@ApiModelProperty(notes="position=4, Package type code")
	String pieceType;
	@ApiModelProperty(notes="position=5, Dimensions")
	Dimension dimensions;
	@ApiModelProperty(notes="position=6, Commodity description")
	String description;

	public Commodity()
	{
		super();
		this.dimensions = new Dimension();
	}

	/**
	 * @return Commodity description
	 */
	public String getDescription()
	{
		return description != null ? description.trim() : "";
	}

	public Dimension getDimensions()
	{
		return dimensions;
	}

	/**
	 * @return Number of pieces
	 */
	public int getPieces()
	{
		return pieces;
	}

	/**
	 * @return Pieces type
	 */
	public String getPieceType()
	{
		return pieceType != null ? pieceType.trim() : "";
	}

	/**
	 * @return the shipClass
	 */
	public double getShipClass() {
		return shipClass;
	}

	/**
	 * @return Total weight
	 */
	public int getWeight()
	{
		return weight;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDimensions(Dimension dims)
	{
		this.dimensions = dims;
	}

	/**
	 * @param Number of pieces
	 */
	public void setPieces(int val) 
	{
		this.pieces = val;
	}

	/**
	 * @param val Pieces type
	 */
	public void setPieceType(String val)
	{
		this.pieceType = val;
	}

	/**
	 * @param shipClass the shipClass to set
	 */
	public void setShipClass(double shipClass) {
		this.shipClass = shipClass;
	}

	/**
	 * @param val Total weight
	 */
	public void setWeight(int val) {
		this.weight = val;
	}
}
