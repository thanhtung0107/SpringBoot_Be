package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.SliderDTO;

import java.util.List;

public interface ISliderService {
    List<SliderDTO> findAll();
    SliderDTO findById(Long id);
    SliderDTO create(SliderDTO Slider);
    SliderDTO update(Long id, SliderDTO Slider);
    boolean delete(Long id);
}
