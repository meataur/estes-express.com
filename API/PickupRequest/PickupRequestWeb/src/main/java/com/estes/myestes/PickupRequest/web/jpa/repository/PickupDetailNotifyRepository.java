package com.estes.myestes.PickupRequest.web.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import com.estes.myestes.PickupRequest.web.jpa.entity.PickupDetailNotify;
import com.estes.myestes.PickupRequest.web.jpa.entity.PickupRequestDetail;

/**
 * 
 * @author rahmaat
 *
 */
public interface PickupDetailNotifyRepository extends PagingAndSortingRepository<PickupDetailNotify, Integer>{
	@Query("SELECT DISTINCT a.notifyPartyType.name FROM PickupDetailNotify a WHERE a.pickupRequestDetail.id=?")
	List<String> findDistinctPartiesByPickupRequestDetailId(Integer id);
	
	@Query("SELECT DISTINCT a.notifyStatusType.name FROM PickupDetailNotify a WHERE a.pickupRequestDetail.id=?")
	List<String> findDistinctStatusTypeByPickupRequestDetailId(Integer id);
	
	@Transactional
	void deleteByPickupRequestDetail(PickupRequestDetail pickupRequestDetail);

}