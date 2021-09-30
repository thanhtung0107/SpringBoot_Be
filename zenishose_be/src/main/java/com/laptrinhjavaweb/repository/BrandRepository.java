package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findByCode(String code);
}
