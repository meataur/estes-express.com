package com.estes.myestes.ImageViewing.web.dao.iface;

import java.util.List;
import java.util.Map;

import com.estes.myestes.ImageViewing.web.dto.ImageRequest;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;

public interface ImageDAO {

	public Map<String, String> writeImageViewRequest(ImageRequest imageRequest, String sessionId);
	
	public List<ImageResult> getImageSearchResults(String requestNumber, String searchType);
	
	public void writeImageFaxRequest(List<ImageResult>imageResults, ImageRequest imageRequest, String sessionId);

}
