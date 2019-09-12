package com.estes.myestes.ImageViewing.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.ImageViewing.web.dao.iface.ImageDAO;
import com.estes.myestes.ImageViewing.web.dto.ImageRequest;
import com.estes.myestes.ImageViewing.web.dto.ImageRequestStatus;
import com.estes.myestes.ImageViewing.web.dto.ImageRequestType;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.service.iface.ImageService;


@Service("imageService")
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private ImageDAO imageDAO;
	
	

	@Override
	public List<ImageResult> processImageRequest(ImageRequest imageRequest, String sessionId) {
		
		Map<String,String> results = imageDAO.writeImageViewRequest(imageRequest, sessionId);
		
		String requestNumber = results.get("requestNumber");
		
		
		
		List<ImageResult> imageResults = new ArrayList<>();
		
		if(requestNumber!=null && !requestNumber.equals("")){
			imageResults = imageDAO.getImageSearchResults(requestNumber,imageRequest.getSearchType());
		}
		
		
		if(imageRequest.getRequestType()==ImageRequestType.F
				&& imageResults!=null 
				&& imageResults.size()>0){
			imageDAO.writeImageFaxRequest(imageResults, imageRequest, sessionId);
		}
		
		if(requestNumber==null || requestNumber.equals("")){
			results.remove("requestNumber");
			for(Map.Entry<String, String> entry : results.entrySet()){
				ImageResult imageResult = new ImageResult();
				imageResult.setSearchType(imageRequest.getSearchType());
				imageResult.setDocType(imageRequest.getDocumentType());
				imageResult.setSearchData(entry.getKey());
				imageResult.setStatus(ImageRequestStatus.ERROR);
				imageResult.setErrorMessage(entry.getValue());
				imageResults.add(imageResult);
			}
		}
		
		
		return imageResults;
	}

	
}
