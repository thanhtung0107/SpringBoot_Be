package com.laptrinhjavaweb.api.web;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.request.OrderDTO;
import com.laptrinhjavaweb.dto.request.OrderServiceDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.service.IOrderService;
import com.laptrinhjavaweb.service.IOrderServiceSer;
import com.laptrinhjavaweb.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController(value = "orderApiOfWeb")
@RequestMapping(SystemConstant.API_VERSION + "/orders")
public class OrderAPI {

    @Autowired
    private IOrderServiceSer orderServiceSer;

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public ResponseDTO showOrder() {
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        result.setData(orderService.findAll(SecurityUtils.getPrincipal().getId()));
        return result;
    }

    @GetMapping("/detail/{id}")
    public ResponseDTO showOrderDetail(@PathVariable("id") Long id) {
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        result.setData(orderService.getOrderDetail(id));
        return result;
    }

    @PostMapping
    public ResponseDTO updateStatus(@RequestBody OrderDTO orderDTO) {
        orderService.updateStatus(orderDTO);
        ResponseDTO result = new ResponseDTO();
        result.setMessage(SystemConstant.SUCCESS_MESSAGE);
        return result;
    }
}
