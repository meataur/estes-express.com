package com.estes.myestes.rating.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.EmailService;
import com.lowagie.text.DocumentException;

import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api(value="Rate Quote PDF Download")
@RestController
public class RateQuotePdfController {
	@Autowired
	EmailService emailService;
	
	@ApiOperation("Download Rate Quote PDF")
	@ApiResponses(value = { 
			@ApiResponse(code=200,responseHeaders={@ResponseHeader(name="Content-Type",description="application/pdf"),@ResponseHeader(name="Content-Disposition",description="attachment; filename=download.pdf")}, message="Download PDF File"),
			@ApiResponse(code=404,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value="/print/{quoteId}",produces="application/pdf")
    public ResponseEntity<?> printRateQuote(@PathVariable String quoteId) throws IOException, DocumentException {

		ByteArrayOutputStream baos = null;
		
		try {
			baos = emailService.getPdfOutPut(quoteId);
		} catch (RatingException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	HttpHeaders header = new HttpHeaders();
    	    header.setContentType(MediaType.APPLICATION_PDF);
    	    header.set(HttpHeaders.CONTENT_DISPOSITION,
    	                   "attachment; filename=Quote-"+quoteId+".pdf");
    	    //header.setContentLength(baos.toByteArray().length);
		return new ResponseEntity<>(baos.toByteArray(),header,HttpStatus.OK);
    }
}
