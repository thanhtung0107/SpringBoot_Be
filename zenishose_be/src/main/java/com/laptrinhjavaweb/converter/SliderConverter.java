package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.SliderDTO;
import com.laptrinhjavaweb.entity.SliderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SliderConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public SliderDTO convertToDTO(SliderEntity entity) {
		SliderDTO dto = modelMapper.map(entity, SliderDTO.class);
		return dto;
	}
	
	public SliderEntity convertToEntity(SliderDTO dto) {
		SliderEntity entity = modelMapper.map(dto, SliderEntity.class);
		return entity;
	}

}
