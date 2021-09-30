package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BrandDTO;

import java.util.List;

public interface IBrandService {
    List<BrandDTO> findAll();
    BrandDTO findById(Long id);
    BrandDTO save(BrandDTO Brand);
    BrandDTO update(Long id, BrandDTO Brand);
    void delete(long[] id);
}
