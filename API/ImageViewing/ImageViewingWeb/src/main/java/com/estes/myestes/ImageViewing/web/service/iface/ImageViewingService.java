package com.estes.myestes.ImageViewing.web.service.iface;

import java.util.List;

import com.estes.myestes.ImageViewing.web.dto.DocumentType;
import com.estes.myestes.ImageViewing.web.dto.Image;
import com.estes.myestes.ImageViewing.web.dto.ImageFax;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.dto.ImageSearch;
import com.estes.myestes.ImageViewing.web.dto.ImageStatusResponse;

public interface ImageViewingService {

	List<DocumentType> getDocumentTypes(String username, String accountNumber);

	List<ImageResult> processImageRequest(ImageSearch imageSearch, String sessionId);
	
	List<String> writeImageFaxRequest(ImageFax imageFax, String sessionId);

	List<Image> getImagesDetails(String key1, String key2, String key3, String key4, String key5, String docType);

	ImageStatusResponse getImageStatus(String requestNumber, String searchData, String docType);

}
