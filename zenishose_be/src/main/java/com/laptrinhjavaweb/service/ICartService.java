package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.customexception.NotPermissionAddToCart;
import com.laptrinhjavaweb.dto.request.CartDTO;

import java.util.List;
import java.util.Map;

public interface ICartService {
	Map<String, Object> findAll(String sessionId);

	CartDTO addToCart(Long productId, CartDTO cartDTO) throws NotPermissionAddToCart;

	void editQuantityCart(Long cartId, CartDTO cartDTO);

	void deleteCart(Long cartId);
}
