/**
 * @author: Todd Allen
 *
 * Creation date: 07/24/2018
 */

package com.estes.myestes.shiptrack.service.iface;

import com.estes.myestes.shiptrack.dto.Image;
import com.estes.myestes.shiptrack.dto.ImageRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

/**
 * Shipment e-mail update service
 */
public interface ImageService
{
	/**
	 * Get images for PRO
	 * 
	 * @param  imgReq {@link ImageRequest} of PRO info
	 * @return  Array of {@link Image} info
	 */
	public Image[] getImages(ImageRequest imgReq) throws ShipTrackException;
}
