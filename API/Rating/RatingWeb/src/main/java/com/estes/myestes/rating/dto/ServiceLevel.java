/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Service level
 */
@ApiModel(description="Service level information")
public class ServiceLevel
{
	@ApiModelProperty(position=2, notes="Service level ID")
	int id;
	@ApiModelProperty(position=3, notes="Text")
	String text;

	public ServiceLevel()
	{
		super();
	} // Constructor

	public ServiceLevel(int i, String txt)
	{
		this();
		setId(i);
		setText(txt);
	} // Constructor

	/**
	 * Get the service level ID
	 * 
	 * @return ID
	 */
	public int getId()
	{
		return id;
	} // getId

	/**
	 * Get the text
	 * 
	 * @return Text
	 */
	public String getText()
	{
		return this.text != null ? text.trim() : "";
	} // getText

	/**
	 * Set the service level ID
	 * 
	 * @param val ID
	 */
	public void setId(int val)
	{
		this.id = val;
	} // setId

	/**
	 * Set the text
	 * 
	 * @param val Text
	 */
	public void setText(String val)
	{
		this.text = val;
	} // setText
}
