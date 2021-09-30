package com.laptrinhjavaweb.api.web;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.dto.customexception.NotPermissionAddToCart;
import com.laptrinhjavaweb.dto.request.CartDTO;
import com.laptrinhjavaweb.dto.request.OrderDTO;
import com.laptrinhjavaweb.dto.response.ProductResponseDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.entity.CartEntity;
import com.laptrinhjavaweb.repository.CartRepository;
import com.laptrinhjavaweb.service.ICartService;
import com.laptrinhjavaweb.service.IOrderService;
import com.laptrinhjavaweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController(value = "cartApiOfWeb")
@RequestMapping(SystemConstant.API_VERSION +"/cart")
public class CartAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/{cartid}/remain")
    public int getQuantityRemainOfProductByCartId(@PathVariable("cartid") Long cartId) {
        Optional<CartEntity> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            ProductResponseDTO product = productService.findById(cart.get().getProduct().getId());
            if (product != null) {
                return product.getNumberOfSell();
            }
        }
        return 0;
    }

    @GetMapping
    public ResponseDTO showCart(@RequestParam(value = "sessionId", required = false) String sessionId) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
        responseDTO.setData(cartService.findAll(sessionId));
        return responseDTO;
    }

    @PostMapping("/{productid}")
    public ResponseDTO addToCart(@PathVariable("productid") Long productId, @RequestBody CartDTO cartDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
            responseDTO.setData(cartService.addToCart(productId, cartDTO));
        } catch (NotPermissionAddToCart e) {
            responseDTO.setMessage(SystemConstant.FAIL_MESSAGE);
        }
        return responseDTO;
    }

    @PostMapping("/{cartid}/quantity")
    public ResponseDTO editQuantityCart(@PathVariable("cartid") Long cartId, @RequestBody CartDTO cartDTO) {
        cartService.editQuantityCart(cartId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
        return responseDTO;
    }

    @DeleteMapping("/{cartid}")
    public ResponseDTO deleteCart(@PathVariable("cartid") Long cartId) {
        cartService.deleteCart(cartId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
        return responseDTO;
    }

    @PostMapping
    public ResponseDTO addOrder(@RequestBody OrderDTO orderDTO) {
        orderService.save(orderDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(SystemConstant.SUCCESS_MESSAGE);
        orderDTO.setCode(orderDTO.getSessionId());
        responseDTO.setData(orderDTO);
        return responseDTO;
    }
}
