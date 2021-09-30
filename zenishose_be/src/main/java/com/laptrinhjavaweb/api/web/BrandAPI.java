package com.laptrinhjavaweb.api.web;

import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "brandApiOfWeb")
@RequestMapping("/api/brand")
public class BrandAPI {

    @Autowired
    private IBrandService brandService;

    @GetMapping
    public ResponseDTO showBrand() {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(brandService.findAll());
        return result;
    }
}
