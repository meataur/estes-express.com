package com.estes.myestes.shiptrack.dao.iface;

import java.util.List;

import com.estes.myestes.shiptrack.dto.ShipmentImage;
import com.estes.myestes.shiptrack.dto.ShipmentImageRequest;

public interface ShipmentImageDAO extends BaseDAO {

	List<ShipmentImage> getImagesForPRO(ShipmentImageRequest imgReq, String type);

	List<ShipmentImage> getWRImagesForPRO(ShipmentImageRequest imgReq);

	boolean hasWRDocuments(String pro);

	void updateWRDocumentInfo(String reqNum, String user);

	String writeImageRequest(ShipmentImageRequest imgRequest, String sessionId, String docType);

	String writeWRRequest(String proNumber, String user);

}
