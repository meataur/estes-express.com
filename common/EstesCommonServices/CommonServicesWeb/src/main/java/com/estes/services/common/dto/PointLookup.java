/**
 * @author: Todd Allen
 *
 * Creation date: 11/15/2018
 */

package com.estes.services.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes points lookup request
 */
@ApiModel(description="Request for Estes points matching a given point")
public class PointLookup
{
	@ApiModelProperty(position=1, notes="Point information")
	Point point;
	@ApiModelProperty(position=2, notes="Maximum number of points to return")
	int maxRows;

	/**
	 * Create new point lookup request
	 */
	public PointLookup()
	{
	} // Constructor

	/**
	 * Get the maximum number of rows to be returned
	 * 
	 * @return Maximum # rows
	 */
	public int getMaxRows()
	{
		return this.maxRows;
	} // getMaxRows

	/**
	 * Get the point
	 * 
	 * @return {@link Point}
	 */
	public Point getPoint()
	{
		return this.point;
	} // getPoint

	/**
	 * Get the maximum number of rows to be returned
	 * 
	 * @param val Maximum # rows
	 */
	public void setMaxRows(int val)
	{
		this.maxRows = val;
	} // setMaxRows

	/**
	 * Set the point
	 * 
	 * @param val {@link Point}
	 */
	public void setPoint(Point val)
	{
		this.point = val;
	} // setPoint
}
