/**
 * @author: Todd Allen
 *
 * Creation date: 04/05/2016
 *
 */

package com.estes.test.ssdr.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.estes.ssdr.rest.message.DocumentResponse;
import com.estes.ssdr.rest.message.FaxNumber;
import com.estes.ssdr.rest.message.ObjectFactory;
import com.estes.ssdr.rest.message.SSDRRequest;
import com.estes.ssdr.rest.message.SSDRResponse;
import com.estes.ssdr.util.DocRetrievalConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * Test ReST call to document image retrieval service
 */
public class RestTemplateTest implements DocRetrievalConstant
{
	/**
	 * ReST web service endpoint
	 */
	public static final String SERVICE_URI = "http://apidev.estesinternal.com/rest/tools/image/request/v1.0";

	private static HttpHeaders createHeaders()
	{
		return new HttpHeaders()
		{
			{
				// Explicitly set Content-Type HTTP header for ESB
				set("Content-Type", "application/json");
			}
		};
	} // createHeaders

	@JsonIgnore
	private static DocumentResponse getDocuments(SSDRRequest req)
	{
		//AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
	    //// make deserializer use JAXB annotations (only)
	    //mapper.getDeserializationConfig().with(introspector);
	    //// make serializer use JAXB annotations (only)
	    //mapper.getSerializationConfig().with(introspector);

		// This works using the JAXB introspector!!!!
/*
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter jaxMsgConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper jaxMapper = new ObjectMapper();
		//AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		jaxMapper.setAnnotationIntrospector(introspector);
		jaxMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // ignore unknown properties
		jaxMsgConverter.setObjectMapper(jaxMapper);
		messageConverters.add(jaxMsgConverter);
*/

		// This works using the Jackson and JAXB introspectors as a pair!!!!
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter jaxMsgConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objMapper = new ObjectMapper();
		//AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = AnnotationIntrospector.pair(secondary, primary);
		objMapper.setAnnotationIntrospector(pair);
		jaxMsgConverter.setObjectMapper(objMapper);
		messageConverters.add(jaxMsgConverter);

		SSDRResponse resp;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
		HttpEntity<SSDRRequest> httpRequest = new HttpEntity<SSDRRequest>(req, createHeaders());
		System.out.println("Request has body? "+httpRequest.hasBody());
		resp = restTemplate.postForObject(SERVICE_URI, httpRequest, SSDRResponse.class);
		//resp = restTemplate.postForObject(SERVICE_URI, req, DocumentResponse.class);
		System.out.println("Response code="+resp.getCode());
		System.out.println("Response message: "+resp.getMessage());
		if (SSDRResponse.isSuccess(resp.getCode())) {
			System.out.println("PRO="+resp.getOTPRO().toString());
			System.out.println("Origin term="+resp.getOT().toString());
		}

		return resp;
    } // getDocuments

	/**
	 * Main method
	 * 
	 * @param args String of arguments
	 */
	public static void main(String[] args)
	{
		ObjectFactory objFac = new ObjectFactory();
		//DocumentRequest request = new DocumentRequest();
		SSDRRequest request = new SSDRRequest();
		request.setSource("W");
		//request.setTrackingNumber("0012345678");
		request.setTrackingNumber("0400931787");
		request.setZip("23323");
		request.setDocumentCodes(objFac.createDocumentCodes());
		request.getDocumentCodes().getDocumentCode().add("DR");
		request.setDelivery("email");
		request.setEmails(objFac.createEmails());
		request.getEmails().getEmail().add("todd.allen@estes-express.com");
		// Add fax info to get past http 500 error for null element
/*
		request.setFaxInfo(objFac.createFaxInfo());
		FaxNumber fax = objFac.createFaxNumber();
		fax.setAreaCode("804");
		fax.setNumber("5551212");
		request.getFaxInfo().setFax(fax);
		request.getFaxInfo().setAttention("");
*/

		// Send ReST service request
		getDocuments(request);
    } // main
}