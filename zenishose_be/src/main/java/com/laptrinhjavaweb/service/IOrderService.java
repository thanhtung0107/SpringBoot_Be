package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.request.OrderDTO;
import com.laptrinhjavaweb.dto.response.ReportResponseDTO;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    List<OrderDTO> findAll();
    List<OrderDTO> findAll(Long userId);
    void save(OrderDTO orderDTO);
    void updateStatus(OrderDTO orderDTO);
    Map<String, Object> getOrderDetail(Long orderId);
    ReportResponseDTO report(String fromDate, String toDate);
}
