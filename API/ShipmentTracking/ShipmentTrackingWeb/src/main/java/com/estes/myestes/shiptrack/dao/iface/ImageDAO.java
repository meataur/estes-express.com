/**
 * @author: Todd Allen
 *
 * Creation date: 07/24/2018
 */

package com.estes.myestes.shiptrack.dao.iface;

import java.util.List;

import com.estes.myestes.shiptrack.dto.Image;
import com.estes.myestes.shiptrack.dto.ImageRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

public interface ImageDAO extends BaseDAO
{
	/**
	 * Get error code
	 * 
	 * @return Error code
	 */
	public String getErrorCode();

	/**
	 * Get images for PRO
	 * 
	 * @param  imgReq {@link ImageRequest} of PRO info
	 * @param  type Type of document - BOL/DR
	 * @return  List of {@link Image} info
	 */
	public List<Image> getImagesForPRO(ImageRequest imgReq, String type) throws ShipTrackException;

	/**
	 * Get WR images for PRO
	 * 
	 * @param  imgReq {@link ImageRequest} of PRO info
	 * @return  List of {@link Image} info
	 */
	public List<Image> getWRImagesForPRO(ImageRequest imgReq) throws ShipTrackException;

	/**
	 * Check whether WR documents exist for PRO
	 * 
	 * @param  pro PRO
	 * @return  Boolean as to whether WR documents found for PRO
	 */
	public boolean hasWRDocuments(String pro) throws ShipTrackException;

	/**
	 * Write BOL/DR document image request
	 * 
	 * @param  reqNum WR document request number
	 * @param  user My Estes user name
	 */
	public void updateWRDocumentInfo(String reqNum, String user) throws ShipTrackException;

	/**
	 * Write BOL/DR document image request
	 * 
	 * @param  imgRequest {@link ImageRequest} request
	 * @param  docType Type of document - BOL/DR
	 * @returns Request number
	 */
	public String writeImageRequest(ImageRequest imgRequest, String docType) throws ShipTrackException;

	/**
	 * Write WR image request
	 * 
	 * @param  pro PRO
	 * @param  user My Estes user name
	 * @returns Request number
	 */
	public String writeWRRequest(String pro, String user) throws ShipTrackException;
}
