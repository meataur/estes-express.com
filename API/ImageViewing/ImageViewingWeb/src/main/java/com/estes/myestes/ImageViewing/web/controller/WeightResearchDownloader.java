package com.estes.myestes.ImageViewing.web.controller;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.ImageViewing.exception.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ResponseHeader;

@Api(description="Download WR Document")
@RestController
public class WeightResearchDownloader {
	
	@Autowired
	private FtpRemoteFileTemplate ftpRemoteFileTemplate;
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")},value = "Download WR Document")
	@ApiResponses(value = { 
			@ApiResponse(code=200,responseHeaders={@ResponseHeader(name="Content-Type",description="application/pdf"),@ResponseHeader(name="Content-Disposition",description="attachment; filename=download.pdf")}, message="Download PDF File"),
			@ApiResponse(code=404,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value="/wr/download",produces="application/pdf")
    public ResponseEntity<?> downloadFile(@RequestParam String fileName) throws IOException {
    	

    	byte[] data = null;
    	
    	try{
    		data = IOUtils.toByteArray(ftpRemoteFileTemplate.getSession().readRaw(fileName));
    	}catch(Exception ex){
    		throw new AppException(HttpStatus.NOT_FOUND, "File Not Found");
    	}
    	
    	 HttpHeaders header = new HttpHeaders();
    	    header.setContentType(MediaType.APPLICATION_PDF);
    	    header.set(HttpHeaders.CONTENT_DISPOSITION,
    	                   "attachment; filename=download.pdf");
    	    header.setContentLength(data.length);
		return new ResponseEntity<>(data,header,HttpStatus.OK);
    }
    
}
