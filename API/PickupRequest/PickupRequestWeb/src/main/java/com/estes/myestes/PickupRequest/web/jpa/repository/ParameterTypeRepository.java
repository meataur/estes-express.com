package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.estes.myestes.PickupRequest.web.jpa.entity.ParameterType;

public interface ParameterTypeRepository extends PagingAndSortingRepository<ParameterType, Integer>{
	
}