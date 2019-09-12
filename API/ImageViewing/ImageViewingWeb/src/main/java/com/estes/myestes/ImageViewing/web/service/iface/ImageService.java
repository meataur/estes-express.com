package com.estes.myestes.ImageViewing.web.service.iface;

import java.util.List;

import com.estes.myestes.ImageViewing.web.dto.ImageRequest;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;

public interface ImageService {
	
	public List<ImageResult> processImageRequest(ImageRequest imageRequest, String sessionId);
	
}
