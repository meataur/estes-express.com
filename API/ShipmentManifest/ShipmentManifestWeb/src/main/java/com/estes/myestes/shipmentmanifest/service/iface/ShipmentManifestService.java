/**
 *
 */

package com.estes.myestes.shipmentmanifest.service.iface;

import java.util.Map;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shipmentmanifest.dto.EmailRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestResponseDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;
import com.estes.myestes.shipmentmanifest.util.ShipmentManifestConstant;

/**
 * Document retrieval service
 */
public interface ShipmentManifestService
{
	/**
	 * ReST service endpoint JNDI name
	 */
	public static final String SERVICE_URI_JNDI = ShipmentManifestConstant.APP_JNDI;
	
	/**
	 * Mail service endpoint JNDI name
	 */
	public static final String MAIL_URI_JNDI = ShipmentManifestConstant.APP_MAIL_JNDI;

	/**
	 * return the manifest results
	 * 
	 * @param the pageable object
	 * @param the manifest request
	 * @param the authentication details
	 * @return ManifestResponse object
	 */
	public Page<ManifestRecordDTO> getManifest(Pageable pageable, ManifestRequestDTO request, Map<String, String> oauthDetails)  throws ShipmentManifestException;
	
	/**
	 * email the manifest results
	 * 
	 * @param the emailed manifest request
	 * @param the format the email attachment should be
	 * @param the email addresses to send to
	 * @param the authentication details
	 * @return ServiceResponse object
	 */
	public ServiceResponse emailManifest(EmailRequestDTO request, Map<String, String> oauthDetails)  throws ShipmentManifestException;
	
}