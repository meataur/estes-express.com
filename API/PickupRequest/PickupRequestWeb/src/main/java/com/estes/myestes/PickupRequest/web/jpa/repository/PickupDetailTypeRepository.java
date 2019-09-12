package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.estes.myestes.PickupRequest.web.jpa.entity.PickupDetailType;
/**
 * 
 * @author rahmaat
 *
 */
public interface PickupDetailTypeRepository extends PagingAndSortingRepository<PickupDetailType, Integer>{

	PickupDetailType findByName(String string);
	
}

