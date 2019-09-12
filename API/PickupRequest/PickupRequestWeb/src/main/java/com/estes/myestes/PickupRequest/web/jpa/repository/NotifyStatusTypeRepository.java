package com.estes.myestes.PickupRequest.web.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.estes.myestes.PickupRequest.web.jpa.entity.NotifyStatusType;

public interface NotifyStatusTypeRepository extends PagingAndSortingRepository<NotifyStatusType, Integer>{

	NotifyStatusType findByName(String notificationType);

}
