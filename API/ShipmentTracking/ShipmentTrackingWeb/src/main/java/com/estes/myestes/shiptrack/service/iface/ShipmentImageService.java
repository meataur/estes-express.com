package com.estes.myestes.shiptrack.service.iface;

import java.util.List;

import com.estes.myestes.shiptrack.dto.ShipmentImage;
import com.estes.myestes.shiptrack.dto.ShipmentImageRequest;

public interface ShipmentImageService {

	List<ShipmentImage> getBolOrDRImages(ShipmentImageRequest imgReq, String sessionId, String type);

	List<ShipmentImage> getWeightAndResearchImages(ShipmentImageRequest imgReq, String username);
}
