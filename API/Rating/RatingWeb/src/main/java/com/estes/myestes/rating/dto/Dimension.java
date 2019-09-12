/**
 * @author: Todd Allen
 *
 * Creation date: 02/05/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Commodity dimensions
 */
@ApiModel(description="Commodity dimensions")
public class Dimension
{
	@ApiModelProperty(position=1, notes="Commodity length (inches)")
	int length;
	@ApiModelProperty(position=2, notes="Commodity width (inches)")
	int width;
	@ApiModelProperty(position=3, notes="Commodity weight (inches)")
	int height;

	/**
	 * Create a new commodity dimension DTO.
	 */
	public Dimension()
	{
		super();
	} // Constructor

	/**
	 * Create a new commodity dimension DTO.
	 * 
	 * @param len Commodity length (inches)
	 * @param wid Commodity width (inches)
	 * @param hgt Commodity height (inches)
	 */
	public Dimension(int len, int wid, int hgt)
	{
		this();
		setLength(len);
		setWidth(wid);
		setHeight(hgt);
	} // Constructor

	/**
	 * Get the commodity height (inches)
	 * 
	 * @return Commodity height
	 */
	public int getHeight()
	{
		return height;
	} // getHeight

	/**
	 * Get the commodity length (inches)
	 * 
	 * @return Commodity length
	 */
	public int getLength()
	{
		return length;
	} // getLength

	/**
	 * Get the commodity width (inches).
	 * 
	 * @return Commodity width
	 */
	public int getWidth()
	{
		return width;
	} // getWidth

	/**
	 * Set the commodity height (inches)
	 * 
	 * @param val Commodity height
	 */
	public void setHeight(int val)
	{
		this.height = val;
	} // setHeight

	/**
	 * Set the commodity length (inches)
	 * 
	 * @param val Commodity width
	 */
	public void setLength(int val)
	{
		this.length = val;
	} // setLength

	/**
	 * Set the commodity width (inches)
	 * 
	 * @param val Commodity width
	 */
	public void setWidth(int val)
	{
		this.width = val;
	} // setWidth
}
