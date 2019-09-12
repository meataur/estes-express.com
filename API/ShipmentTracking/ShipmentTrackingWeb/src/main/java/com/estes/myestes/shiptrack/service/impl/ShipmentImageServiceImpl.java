package com.estes.myestes.shiptrack.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.estes.myestes.shiptrack.dao.iface.ShipmentImageDAO;
import com.estes.myestes.shiptrack.dto.ShipmentImage;
import com.estes.myestes.shiptrack.dto.ShipmentImageRequest;
import com.estes.myestes.shiptrack.exception.AppException;
import com.estes.myestes.shiptrack.service.iface.ShipmentImageService;

/**
 * 
 * @author Ataur Rahman
 *
 */
@Service("shipmentImageService")
@Scope("prototype")
public class ShipmentImageServiceImpl implements ShipmentImageService{
	
	@Autowired
	private ShipmentImageDAO shipmentImageDAO;
	
	@Override
	public List<ShipmentImage> getBolOrDRImages(ShipmentImageRequest imgReq, String sessionId, String type)
	{
		
		
		if (imgReq.getRequestNumber()==null || imgReq.getRequestNumber().trim().equals("")) {
			
			String requestNumber = shipmentImageDAO.writeImageRequest(imgReq, sessionId, type);
			
			imgReq.setRequestNumber(requestNumber);
		}

		return shipmentImageDAO.getImagesForPRO(imgReq, type);
	}
	
	@Override
	public List<ShipmentImage> getWeightAndResearchImages(ShipmentImageRequest imgReq, String username)
	{
		if(shipmentImageDAO.hasWRDocuments(imgReq.getProNumber())) {
			
			if (imgReq.getRequestNumber()==null || imgReq.getRequestNumber().trim().equals("")) {
				imgReq.setRequestNumber(shipmentImageDAO.writeWRRequest(imgReq.getProNumber(), username));
			}
			
			shipmentImageDAO.updateWRDocumentInfo(imgReq.getRequestNumber(), username);
			return shipmentImageDAO.getWRImagesForPRO(imgReq);
		}
		
		throw new AppException(HttpStatus.NOT_FOUND, "error","No image found");	
	}
}
