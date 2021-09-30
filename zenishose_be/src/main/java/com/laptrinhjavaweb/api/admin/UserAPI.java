package com.laptrinhjavaweb.api.admin;

import com.laptrinhjavaweb.dto.AuthToken;
import com.laptrinhjavaweb.dto.SearchDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.dto.request.RequestDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController(value = "userApiOfAdmin")
@RequestMapping("/api/admin/user")
public class UserAPI {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private UserService userService1;

    @GetMapping
    public ResponseDTO showUser() {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(userService.findAll());
        return result;
    }

    @GetMapping("/{id}")
    public ResponseDTO showUserDetail(@PathVariable("id") long id) {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(userService.findById(id));
        return result;
    }

    @PostMapping
    public ResponseDTO createUser(@RequestBody UserDTO newUser) {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(userService.create(newUser));
        return result;
    }

    @PutMapping("/{id}")
    public ResponseDTO updateUser(@PathVariable("id") long id, @RequestBody UserDTO updateUser) {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(userService.update(id, updateUser));
        return result;
    }

    @DeleteMapping
    public ResponseDTO deleteUser(@RequestBody RequestDTO requestDTO) {
        ResponseDTO result = new ResponseDTO();
        userService.delete(requestDTO.getIds());
        result.setMessage("success");
        return result;
    }
    @PutMapping("/checkpass/{id}")
    public ResponseDTO checkpass(@PathVariable("id") long id,@RequestBody UserDTO change) {
        ResponseDTO result = new ResponseDTO();
        result.setMessage("success");
        result.setData(userService1.changepass(id,change));
        return result;
    }

}
