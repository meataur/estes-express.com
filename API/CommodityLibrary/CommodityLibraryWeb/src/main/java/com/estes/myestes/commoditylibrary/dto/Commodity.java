/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.commoditylibrary.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes Commodity
 */
public class Commodity
{
	@ApiModelProperty(notes=" Commodity id ")
	String id;
	@ApiModelProperty(notes=" Is Hazardous ? - Y/N")
	boolean hazmat = false;
	@ApiModelProperty(notes=" Quantity of the package")
	int goodsQuantity;
	@ApiModelProperty(notes=" Package type ")
	String goodsType;
	@ApiModelProperty(notes=" Package description ")
	String description;
	@ApiModelProperty(notes=" Package weight ")
	int weight;
	@ApiModelProperty(notes=" Nmfc value ")
	String nmfc = "";
	@ApiModelProperty(notes=" Nmfc Sub value ")
	String nmfcSub = "";
	@ApiModelProperty(notes=" Ship class ")
	String shipClass = "";
	@ApiModelProperty(notes=" Declared value ")
	double declaredValue;
	@ApiModelProperty(notes=" Dimensions of the commodity ")
	Dimension dimensions;
	
	public Commodity() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*public boolean isHazmat() {
		return hazmat;
	}

	public void setHazmat(boolean hazmat) {
		this.hazmat = hazmat;
	}*/
	
	/**
	 * Get the hazmat indicator (Y/).
	 * 
	 * @return Hazmat indicator (Y/)
	 */
	public String getHazmat()
	{
		return this.hazmat?"Y":"";
	} // getHazmat

	/**
	 * Set the hazmat indicator (Y/N).
	 * 
	 * @param tf Hazmat indicator
	 */
	public void setHazmat(String yn)
	{
		if (yn != null) {
			this.hazmat = yn.equalsIgnoreCase("Y");
		}
		else {
			this.hazmat = false;
		}
	} // setHazmat

	/**
	 * @return the goodsQuantity
	 */
	public int getGoodsQuantity() {
		return goodsQuantity;
	}

	/**
	 * @param goodsQuantity the goodsQuantity to set
	 */
	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the nmfc
	 */
	public String getNmfc() {
		return nmfc;
	}

	/**
	 * @param nmfc the nmfc to set
	 */
	public void setNmfc(String nmfc) {
		this.nmfc = nmfc;
	}

	/**
	 * @return the nmfcSub
	 */
	public String getNmfcSub() {
		return nmfcSub;
	}

	/**
	 * @param nmfcSub the nmfcSub to set
	 */
	public void setNmfcSub(String nmfcSub) {
		this.nmfcSub = nmfcSub;
	}

	/**
	 * @return the shipClass
	 */
	public String getShipClass() {
		// Strip off the decimal places when value is 0
		return shipClass != null ? shipClass.replaceAll("\\.0*$", "") : null;
	}

	/**
	 * @param shipClass the shipClass to set
	 */
	public void setShipClass(String shipClass) {
		this.shipClass = shipClass;
	}

	/**
	 * @return the declaredValue
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public Dimension getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}
	
	/**
	 * Set the commodity dimensions.
	 * 
	 * @param len Commodity length (inches)
	 * @param wid Commodity width (inches)
	 * @param hgt Commodity height (inches)
	 */
	public void setDimension(int len, int wid, int hgt)
	{
		this.dimensions = new Dimension(len, wid, hgt);
	} // setDimension	
}
