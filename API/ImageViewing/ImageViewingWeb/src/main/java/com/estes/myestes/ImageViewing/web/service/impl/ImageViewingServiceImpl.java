package com.estes.myestes.ImageViewing.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.ImageViewing.web.dao.iface.ImageViewingDAO;
import com.estes.myestes.ImageViewing.web.dto.DocumentType;
import com.estes.myestes.ImageViewing.web.dto.Image;
import com.estes.myestes.ImageViewing.web.dto.ImageFax;
import com.estes.myestes.ImageViewing.web.dto.ImageRequestStatus;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.dto.ImageSearch;
import com.estes.myestes.ImageViewing.web.dto.ImageStatusResponse;
import com.estes.myestes.ImageViewing.web.service.iface.ImageViewingService;

@Service("imageViewingService")
public class ImageViewingServiceImpl implements ImageViewingService{

	@Autowired
	private ImageViewingDAO imageViewingDAO;
	
	@Override
	public List<DocumentType> getDocumentTypes(String username, String accountNumber) {
		return imageViewingDAO.getDocumentTypes(username,accountNumber);
	}

	@Override
	public List<ImageResult> processImageRequest(ImageSearch imageSearch, String sessionId) {
		
		Map<String,String> results = imageViewingDAO.writeImageViewRequest(imageSearch, sessionId);
		
		String requestNumber = results.get("requestNumber");
		
		
		
		List<ImageResult> imageResults = new ArrayList<>();
		
		if(requestNumber!=null && !requestNumber.equals("")){
			imageResults = imageViewingDAO.getImageSearchResults(requestNumber,imageSearch.getSearchType());
		}
		
		if(requestNumber==null || requestNumber.equals("")){
			results.remove("requestNumber");
			for(Map.Entry<String, String> entry : results.entrySet()){
				ImageResult imageResult = new ImageResult();
				imageResult.setSearchType(imageSearch.getSearchType());
				imageResult.setDocType(imageSearch.getDocumentType());
				imageResult.setSearchData(entry.getKey());
				imageResult.setStatus(ImageRequestStatus.ERROR);
				imageResult.setErrorMessage(entry.getValue());
				imageResults.add(imageResult);
			}
		}
		
		
		return imageResults;
	}
	
	@Override
	public List<String>  writeImageFaxRequest(ImageFax imageFax,String sessionId) {
		return imageViewingDAO.writeImageFaxRequest(imageFax, sessionId);
	}

	@Override
	public List<Image> getImagesDetails(String key1, String key2, String key3, String key4, String key5,
			String docType) {
		List<Image> images = imageViewingDAO.getImagesDetails(key1, key2, key3, key4, key5, docType);
		return images;
	}

	@Override
	public ImageStatusResponse getImageStatus(String requestNumber, String searchData, String docType) {
		ImageStatusResponse results = imageViewingDAO.getImageStatus(requestNumber, searchData, docType);
		
		return results;
	}

	
}
