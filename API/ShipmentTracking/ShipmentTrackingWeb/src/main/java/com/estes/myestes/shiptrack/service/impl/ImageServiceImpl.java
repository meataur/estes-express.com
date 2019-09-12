/**
 * @author: Todd Allen
 *
 * Creation date: 07/24/2018
 */

package com.estes.myestes.shiptrack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.shiptrack.dao.iface.ErrorDAO;
import com.estes.myestes.shiptrack.dao.iface.ImageDAO;
import com.estes.myestes.shiptrack.dto.Image;
import com.estes.myestes.shiptrack.dto.ImageRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.ImageService;

@Service("imageService")
@Scope("prototype")
public class ImageServiceImpl implements ImageService
{
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private ErrorDAO errorDAO;

	/**
	 * Create a new service
	 */
	public ImageServiceImpl() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.shiptrack.service.iface.ImageService#getImagesForPRO()
	 */
	public Image[] getImages(ImageRequest imgReq) throws ShipTrackException
	{
		List<Image> images;

		// Remove dash from PRO
		imgReq.setPro(imgReq.getPro().replace("-", ""));

		/*
		 * Get BOL images
		 */
		// Write image request if request ID not present
		if (ImageRequest.isRequestNumEmpty(imgReq.getBolRequestNum())) {
			imgReq.setBolRequestNum(imageDAO.writeImageRequest(imgReq, Image.BOL));
		}
		if (!ImageRequest.isRequestNumEmpty(imgReq.getBolRequestNum())) {
			images = imageDAO.getImagesForPRO(imgReq, Image.BOL);
		}
		// No request ID - cannot retrieve image
		else {
			Image err = new Image(imgReq.getPro(), Image.BOL, false);
			err.getErrorInfo().setErrorCode(imageDAO.getErrorCode());
			err.getErrorInfo().setMessage(errorDAO.getErrorMessage(imageDAO.getErrorCode()));
			images = new ArrayList<Image>();
			images.add(err);
		}

		/*
		 * Get DR images
		 */
		// Write image request if request ID not present
		if (ImageRequest.isRequestNumEmpty(imgReq.getDrRequestNum())) {
			imgReq.setDrRequestNum(imageDAO.writeImageRequest(imgReq, Image.DR));
		}
		if (!ImageRequest.isRequestNumEmpty(imgReq.getDrRequestNum())) {
			List<Image> drImages = imageDAO.getImagesForPRO(imgReq, Image.DR);
			if (!drImages.isEmpty()) {
				images.addAll(drImages);
			}
		}
		// No request ID - cannot retrieve image
		else {
			Image err = new Image(imgReq.getPro(), Image.DR, false);
			err.getErrorInfo().setErrorCode(imageDAO.getErrorCode());
			err.getErrorInfo().setMessage(errorDAO.getErrorMessage(imageDAO.getErrorCode()));
			images.add(err);
		}

		/*
		 * Get WR images
		 */
		// Get WR images only for payor
		if (imgReq.isPayor()){
			if(imageDAO.hasWRDocuments(imgReq.getPro())) {
				// Write WR image request if request ID not present
				if (ImageRequest.isRequestNumEmpty(imgReq.getWrRequestNum())) {
					imgReq.setWrRequestNum(imageDAO.writeWRRequest(imgReq.getPro(), imgReq.getUser()));
					imageDAO.updateWRDocumentInfo(imgReq.getWrRequestNum(), imgReq.getUser());
				}
				List<Image> wrImages = imageDAO.getWRImagesForPRO(imgReq);
				if (!wrImages.isEmpty()) {
					// Set request ID on all images
					for (Image img : wrImages) {
							img.setRequestNum(imgReq.getWrRequestNum());
					}
					images.addAll(wrImages);
				}
			}
			else {
				Image err = new Image(imgReq.getPro(), Image.WR, false);
				images.add(err);
			}
		}

		return images.toArray( new Image[images.size()] );
	} // getImagesForPRO
}
