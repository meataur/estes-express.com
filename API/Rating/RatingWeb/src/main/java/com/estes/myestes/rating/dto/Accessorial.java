/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Estes accessorial information
 */
@ApiModel(description="Estes accessorial charge information")
@Data
public class Accessorial
{
	@ApiModelProperty(position=1, notes="Accessorial code")
	String code;
	@ApiModelProperty(position=2, notes="Application ID - LTL=standard LTL/TC=time critical/VTL=volume truckload")
	String appID;
	@ApiModelProperty(position=3, notes="Display on screen?")
	boolean display;
	@ApiModelProperty(position=4, notes="Description")
	String description;

	/**
	 * Create new accessorial DTO
	 */
	public Accessorial()
	{
	} // Constructor

	/**
	 * Create new point DTO
	 */
	public Accessorial(String cod, String desc)
	{
		this();
		this.code = cod;
		this.description = desc;
	} // Constructor

	/**
	 * Get the app ID
	 * 
	 * @return App ID
	 */
	public String getAppID()
	{
		return this.appID;
	} // getAppID

	/**
	 * Get the accessorial code
	 * 
	 * @return Accessorial code
	 */
	public String getCode()
	{
		return this.code.toUpperCase().trim();
	} // getCode

	/**
	 * Get the accessorial description
	 * 
	 * @return Description
	 */
	public String getDescription()
	{
		return this.description.trim();
	} // getDescription

	/**
	 * Get the screen display indicator
	 * 
	 * @return Display indicator
	 */
	public boolean getDisplay()
	{
		return this.display;
	} // getDisplay

	/**
	 * Get the app ID
	 * 
	 * @param val App ID
	 */
	public void setAppID(String val)
	{
		this.appID = val;
	} // setAppID

	/**
	 * Set the accessorial code
	 * 
	 * @param val Code
	 */
	public void setCode(String val)
	{
		this.code = val;
	} // setCode

	/**
	 * Set the description
	 * 
	 * @param val Description
	 */
	public void setDescription(String val)
	{
		this.description = val;
	} // setDescription

	/**
	 * Set the screen display indicator
	 * 
	 * @param val Display indicator
	 */
	public void setDisplay(boolean bool)
	{
		this.display = bool;
	} // setDisplay
}
