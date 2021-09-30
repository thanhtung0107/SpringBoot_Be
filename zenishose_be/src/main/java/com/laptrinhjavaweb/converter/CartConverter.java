package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.request.CartDTO;
import com.laptrinhjavaweb.entity.CartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CartDTO convertToDTO(CartEntity entity) {
		CartDTO dto = modelMapper.map(entity, CartDTO.class);
		return dto;
	}
	
	public CartEntity convertToEntity(CartDTO dto) {
		CartEntity entity = modelMapper.map(dto, CartEntity.class);
		return entity;
	}

}
