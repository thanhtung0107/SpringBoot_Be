package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.SliderConverter;
import com.laptrinhjavaweb.dto.SliderDTO;
import com.laptrinhjavaweb.service.ISliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SliderService implements ISliderService {

    //@Autowired
    //private SliderRepository sliderRepository;

    @Autowired
    private SliderConverter sliderConverter;

    @Override
    public List<SliderDTO> findAll() {
        //return sliderRepository.findAll().stream().map(item -> sliderConverter.convertToDTO(item)).collect(Collectors.toList());
        return null;
    }

    @Override
    public SliderDTO findById(Long id) {
        //return sliderConverter.convertToDTO(sliderRepository.findById(id).get());
        return null;
    }

    @Override
    public SliderDTO create(SliderDTO newSlider) {
        //return sliderConverter.convertToDTO(sliderRepository.save(sliderConverter.convertToEntity(newSlider)));
        return null;
    }

    @Override
    public SliderDTO update(Long id, SliderDTO Slider) {
        //return sliderConverter.convertToDTO(sliderRepository.save(sliderConverter.convertToEntity(Slider)));
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try{
            //sliderRepository.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return false;
    }

}
