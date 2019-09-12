package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.estes.myestes.PickupRequest.web.jpa.entity.NotifyPartyType;

public interface NotifyPartyTypeRepository extends PagingAndSortingRepository<NotifyPartyType, Integer>{

	NotifyPartyType findByName(String partyName);

}
