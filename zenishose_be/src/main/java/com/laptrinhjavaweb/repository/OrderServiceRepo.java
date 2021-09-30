package com.laptrinhjavaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhjavaweb.entity.OrderServiceEntity;

@Repository
public interface OrderServiceRepo extends JpaRepository<OrderServiceEntity, Long> {
	 List<OrderServiceEntity> findByUserEntity_Id(Long userId);
}
