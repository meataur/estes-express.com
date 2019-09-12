/**
 * @author: Lakshman K
 *
 * Creation date: 12/11/2018
 *
 */

package com.estes.myestes.commoditylibrary.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Data transfer object for commodity dimensions
 */
public class Dimension
{
	@ApiModelProperty(notes=" Commodity length ")
	int length;
	@ApiModelProperty(notes=" Commodity width ")
	int width;
	@ApiModelProperty(notes=" Commodity weight ")
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