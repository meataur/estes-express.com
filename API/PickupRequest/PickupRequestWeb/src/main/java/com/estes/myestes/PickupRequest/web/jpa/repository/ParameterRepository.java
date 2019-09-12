package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.estes.myestes.PickupRequest.web.jpa.entity.Parameter;

public interface ParameterRepository extends PagingAndSortingRepository<Parameter, Integer>{
	
}
