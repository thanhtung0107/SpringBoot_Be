package com.laptrinhjavaweb.api.web;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.customexception.NotPermissionAddToCart;
import com.laptrinhjavaweb.dto.request.OrderServiceDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.entity.ServiceEntity;
import com.laptrinhjavaweb.service.impl.OrderServiceSer;
import com.laptrinhjavaweb.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(SystemConstant.API_VERSION + "/orderservice")
public class OrderServiceAPI {

    @Autowired
    private OrderServiceSer service;

    @PostMapping
    public ResponseDTO addToCart(@RequestBody OrderServiceDTO dto) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
            responseDTO.setData(service.save(dto));
        } catch (NotPermissionAddToCart e) {
            responseDTO.setMessage(SystemConstant.FAIL_MESSAGE);
        }
        return responseDTO;
    }

    // day la api hiển thị theo user
    @GetMapping("/user")
    public ResponseDTO showOrderByUser() {
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        System.out.println(SecurityUtils.getPrincipal().getId());
        result.setData(service.findAll(SecurityUtils.getPrincipal().getId()));
        return result;
    }

    @GetMapping("/get-all-type")
    public List<ServiceEntity> getAllServiceType() {
        List<ServiceEntity> list = service.getAllServiceType();
        return list;
    }
}
