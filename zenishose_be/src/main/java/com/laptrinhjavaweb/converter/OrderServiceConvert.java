package com.laptrinhjavaweb.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laptrinhjavaweb.dto.request.OrderServiceDTO;
import com.laptrinhjavaweb.entity.OrderServiceEntity;
@Component
public class OrderServiceConvert {
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderServiceDTO convertToDTO(OrderServiceEntity entity) {
		OrderServiceDTO dto = modelMapper.map(entity, OrderServiceDTO.class);
		return dto;
	}
	
	public OrderServiceEntity convertToEntity(OrderServiceDTO dto) {
		OrderServiceEntity entity = modelMapper.map(dto, OrderServiceEntity.class);
		return entity;
	}

}
