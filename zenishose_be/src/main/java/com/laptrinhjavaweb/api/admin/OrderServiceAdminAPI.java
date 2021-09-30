package com.laptrinhjavaweb.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.request.OrderServiceDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.service.impl.OrderServiceSer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(SystemConstant.API_VERSION +"/admin/orderservices")

public class OrderServiceAdminAPI {
	@Autowired 
	private OrderServiceSer service;
	
	// đây là hàm show tất cả thông tin
    @GetMapping
    public ResponseDTO showOrder() {
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        result.setData(service.findAll());
        return result;
    }
    @PostMapping
    public ResponseDTO updateStatus(@RequestBody OrderServiceDTO orderServiceDTO) {
    	service.updateStatus(orderServiceDTO);
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        return result;
    }
}
