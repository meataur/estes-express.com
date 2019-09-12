/**
 * @author: Todd Allen
 *
 * Creation date: 03/14/2019
 */

package com.estes.myestes.rating.dao.iface;

import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rating request comments
 */
public interface CommentsDAO extends BaseDAO
{
	/**
	 * Get comments for quote
	 * 
	 * @param id Unique quote ID
	 * @param app Quote request type
	 * @return Comments
	 */
	public String getComments(String id, String app);

	/**
	 * Stage comments received in request
	 * 
	 * @param id Unique quote ID
	 * @param req (@RatingRequest}
	 */
	public void stageComments(String id, RatingRequest request) throws RatingException;
}
