package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.estes.myestes.PickupRequest.web.jpa.entity.PickupRequestDetail;

/**
 * JPA Repository
 * @author rahmaat
 *
 */
public interface PickupRequestDetailRepository extends PagingAndSortingRepository<PickupRequestDetail, Integer>{
	
	/**
	 * find PickupRequestDetail by username and picupDetailType name (DEFAULT)
	 * @param username
	 * @param pickupDetailTypeName
	 * @return PickupRequestDetail
	 */
	@Transactional(readOnly=true)
	PickupRequestDetail findByUsernameAndPickupDetailType_Name(String username, String pickupDetailTypeName);

	/**
	 * find PickupRequestDetail username, templateName and pickupDetailType name(TEMPLATE)
	 * @param username
	 * @param templateName
	 * @param pickupDetailTypeName
	 * @return PickupRequestDetail
	 */
	@Transactional(readOnly=true)
	PickupRequestDetail findByUsernameAndTemplateNameAndPickupDetailType_Name(String username, String templateName,String pickupDetailTypeName);
	
	/**
	 * find PickupRequestDetail username, bolNumber and pickupDetailType name(DRAFT)
	 * @param username
	 * @param bolNumber
	 * @param pickupDetailTypeName
	 * @return
	 */
	@Transactional(readOnly=true)
	PickupRequestDetail findByUsernameAndBolNoAndPickupDetailType_Name(String username, String bolNumber,String pickupDetailTypeName);
	
	/**
	 * find PickupRequestDetail username, bolSeqNo and pickupDetailType name(HISTORY)
	 * @param username
	 * @param bolId
	 * @param pickupDetailTypeName
	 * @return PickupRequestDetail
	 */
	@Transactional(readOnly=true)
	PickupRequestDetail findByUsernameAndBolSeqNoAndPickupDetailType_Name(String username, String bolId, String pickupDetailTypeName);

}
