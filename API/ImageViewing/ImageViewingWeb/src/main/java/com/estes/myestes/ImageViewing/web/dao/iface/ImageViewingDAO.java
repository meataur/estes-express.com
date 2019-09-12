package com.estes.myestes.ImageViewing.web.dao.iface;

import java.util.List;
import java.util.Map;

import com.estes.myestes.ImageViewing.web.dto.DocumentType;
import com.estes.myestes.ImageViewing.web.dto.Image;
import com.estes.myestes.ImageViewing.web.dto.ImageFax;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.dto.ImageSearch;
import com.estes.myestes.ImageViewing.web.dto.ImageStatusResponse;

public interface ImageViewingDAO {

	List<DocumentType> getDocumentTypes(String username, String accountNumber);

	Map<String, String> writeImageViewRequest(ImageSearch imageSearch, String sessionId);

	List<String> writeImageFaxRequest(ImageFax imageFax, String sessionId);

	ImageStatusResponse getImageStatus(String requestNumber, String searchData, String docType);

	List<Image> getImagesDetails(String key1, String key2, String key3, String key4, String key5, String docType);

	List<ImageResult> getImageSearchResults(String requestNumber, String searchType);
}
