/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes food warehouse info
 */
@ApiModel(description="Food warehouse")
public class FoodWarehouse
{
	@ApiModelProperty(notes="Unique identifier")
	int id;
	@ApiModelProperty(notes="Name")
	String name;

	/**
	 * Create new point DTO
	 */
	public FoodWarehouse()
	{
	} // Constructor

	/**
	 * Create new food warehouse DTO
	 */
	public FoodWarehouse(int i, String nam)
	{
		this();
		this.id = i;
		this.name = nam;
	} // Constructor

	/**
	 * Get the ID
	 * 
	 * @return ID
	 */
	public int getId()
	{
		return this.id;
	} // getId

	/**
	 * Get the food warehouse name
	 * 
	 * @return Name
	 */
	public String getName()
	{
		return this.name.trim();
	} // getName

	/**
	 * Get the ID
	 * 
	 * @param val ID
	 */
	public void setId(int val)
	{
		this.id = val;
	} // setId

	/**
	 * Set the name
	 * 
	 * @param val name
	 */
	public void setName(String val)
	{
		this.name = val;
	} // setName
}
