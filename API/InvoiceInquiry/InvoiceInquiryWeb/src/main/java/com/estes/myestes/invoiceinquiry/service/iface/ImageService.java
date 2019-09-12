/**
 * @author: Todd Allen
 *
 * Creation date: 12/31/2018
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import com.estes.myestes.invoiceinquiry.dto.ImageRequest;
import com.estes.myestes.invoiceinquiry.dto.Image;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to retrieve document images associated with PRO
 */
public interface ImageService
{
	public static final String FOUND = "Y";
	public static final String NOT_FOUND = "N"; 
	public static final String WORKING = "W";

	/**
	 * Get scanned document info
	 * 
	 * @param accont Customer account code
	 * @param session Session ID
	 * @param request {@link ImageRequest} info
	 * @return Retrieved {@link Image} info
	 */
	public Image getImageInfo(String account, String session, ImageRequest request) throws InvoiceException;
}
