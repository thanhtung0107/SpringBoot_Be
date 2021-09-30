package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}
