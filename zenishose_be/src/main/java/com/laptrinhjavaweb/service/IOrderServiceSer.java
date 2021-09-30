package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.dto.customexception.NotPermissionAddToCart;
import com.laptrinhjavaweb.dto.request.OrderServiceDTO;
import com.laptrinhjavaweb.entity.ServiceEntity;


public interface IOrderServiceSer {
    List<OrderServiceDTO > findAll();
    List<ServiceEntity> getAllServiceType();
    List<OrderServiceDTO> findAll(Long userId);
    OrderServiceDTO save(OrderServiceDTO dto) throws NotPermissionAddToCart;
    void updateStatus(OrderServiceDTO orderDTO);
    void deleteOrderServic(Long cartId);

}
