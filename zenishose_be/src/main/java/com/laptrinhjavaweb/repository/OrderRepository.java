package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserEntity_Id(Long userId);
}
