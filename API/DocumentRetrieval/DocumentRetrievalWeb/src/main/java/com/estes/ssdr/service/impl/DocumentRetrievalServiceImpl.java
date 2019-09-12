/**
 * @author: Todd Allen
 *
 * Creation date: 03/11/2016
 *
 */

package com.estes.ssdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estes.framework.logger.ESTESLogger;
import com.estes.ssdr.dto.DocumentRequestDTO;
import com.estes.ssdr.dto.DocumentResponseDTO;
import com.estes.ssdr.exception.DocumentRetrievalException;
import com.estes.ssdr.rest.message.FaxNumber;
import com.estes.ssdr.rest.message.ObjectFactory;
import com.estes.ssdr.rest.message.SSDRRequest;
import com.estes.ssdr.rest.message.SSDRResponse;
import com.estes.ssdr.service.iface.DocumentRetrievalService;
import com.estes.ssdr.util.DocRetrievalConstant;
//import com.estesexpress.www.toolbox.utils.JndiLookup;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * Document retrieval service implementation 
 */
@Service("documentRetrievalService")
@Scope("prototype")
public class DocumentRetrievalServiceImpl implements DocumentRetrievalService, DocRetrievalConstant
{
	/**
	 * Create a new service
	 */
	public DocumentRetrievalServiceImpl()
	{
		super();
	} // Constructor

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

	/**
	 * (non-Javadoc)
	 * @see com.estes.ssdr.service.iface.DocumentRetrievalService#invokeService()
	 */
	public DocumentResponseDTO invokeService(SSDRRequest req) throws DocumentRetrievalException
	{
		// Use the Jackson and JAXB introspectors as a pair.
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter jaxMsgConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objMapper = new ObjectMapper();
		/*
		 * Jackson introspector needed for @JsonIgnoreProperties and @JsonInclude annotations
		 * JAXB introspector is needed to handle the uppercase element names in the response
		 */
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		//AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		AnnotationIntrospector pair = AnnotationIntrospector.pair(primary, secondary);
		objMapper.setAnnotationIntrospector(pair);
		jaxMsgConverter.setObjectMapper(objMapper);
		messageConverters.add(jaxMsgConverter);

		SSDRResponse resp = null;
		RestTemplate restTemplate = new RestTemplate();
		// Set message converter with Jackson and JAXB introspectors in RestTemplate
		restTemplate.setMessageConverters(messageConverters);
		HttpEntity<SSDRRequest> httpRequest = new HttpEntity<SSDRRequest>(req, createHeaders());
		try {
			String endpoint = (String) new JndiTemplate().lookup(SERVICE_URI_JNDI);
			resp = restTemplate.postForObject(endpoint, httpRequest, SSDRResponse.class);
		}
		catch (Exception e) {
            ESTESLogger.log(ESTESLogger.ERROR, DocumentRetrievalServiceImpl.class, "invokeService()", "ReST call failed", e);
			throw new DocumentRetrievalException(e);
		}

		return mapResponse(resp);
	} // invokeService

	/**
	 * (non-Javadoc)
	 * @see com.estes.ssdr.service.iface.DocumentRetrievalService#mapResponse()
	 */
	public DocumentResponseDTO mapResponse(SSDRResponse response)
	{
		DocumentResponseDTO dto = new DocumentResponseDTO();
		dto.setCode(response.getCode());
		dto.setMessage(response.getMessage());
		dto.setPro(response.getOTPRO());
		dto.setDestTerminal(response.getDT());
		dto.setDestTermPhone(response.getDTPhone());
		dto.setOriginTerminal(response.getOT());
		dto.setOriginTermPhone(response.getOTPhone());
		return dto;
	} // mapResponse

	/**
	 * (non-Javadoc)
	 * @see com.estes.ssdr.service.iface.DocumentRetrievalService#submitRequest()
	 */
	@Override
	public DocumentResponseDTO submitRequest(DocumentRequestDTO dto) throws DocumentRetrievalException
	{
		ObjectFactory objFac = new ObjectFactory();
		SSDRRequest request = new SSDRRequest();
		request.setSource(WEB_SOURCE);
		request.setTrackingNumber(dto.getTrackingNum());
		request.setZip(dto.getDestZip());
		request.setDocumentCodes(objFac.createDocumentCodes());
		// Parse out document types
		String[] codes = dto.getDocumentTypes().split(",");
		for (String token : codes) {
			request.getDocumentCodes().getDocumentCode().add(token);
		}
		request.setDelivery(dto.getDeliveryMethod());
		if (DocumentRequestDTO.hasEmail(dto)) {
			request.setEmails(objFac.createEmails());
			// Parse out e-mail addresses
			String[] emails = dto.getEmail().split(",");
			for (String token : emails) {
				request.getEmails().getEmail().add(token);
			}
		}
		else if (DocumentRequestDTO.hasFax(dto)){
			// Parse fax number
			String faxDigits = dto.getFax().replaceAll("[\\D]", "");
			request.setFaxInfo(objFac.createFaxInfo());
			FaxNumber fax = objFac.createFaxNumber();
			fax.setAreaCode(faxDigits.substring(0, 3));
			fax.setNumber(faxDigits.substring(3, 10));
			request.getFaxInfo().setFax(fax);
			request.getFaxInfo().setAttention(dto.getFaxAttention());
		}

		// Send ReST service request
		return invokeService(request);
	} // submitRequest
}