package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.CartConverter;
import com.laptrinhjavaweb.dto.MyUser;
import com.laptrinhjavaweb.dto.customexception.NotPermissionAddToCart;
import com.laptrinhjavaweb.dto.request.CartDTO;
import com.laptrinhjavaweb.entity.CartEntity;
import com.laptrinhjavaweb.entity.ProductEntity;
import com.laptrinhjavaweb.repository.CartRepository;
import com.laptrinhjavaweb.repository.ProductRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.ICartService;
import com.laptrinhjavaweb.util.SecurityUtils;
import com.laptrinhjavaweb.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Map<String, Object> findAll(String sessionId) {
		Map<String, Object> result = new HashMap<>();
		List<CartDTO> cartDTOS = new ArrayList<>();
		int total = 0;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<CartEntity> cartEntities = null;
		if (auth != null && !auth.getPrincipal().equals("anonymousUser")) {
			MyUser myUser = SecurityUtils.getPrincipal();
			cartEntities = cartRepository.findByUser_Id(myUser.getId());
		} else if (sessionId != null) {
			cartEntities = cartRepository.findBySessionId(sessionId);
		}
		if (cartEntities != null && cartEntities.size() > 0) {
			for (CartEntity item : cartEntities) {
				CartDTO cartDTO = new CartDTO();
				cartDTO.setProductName(item.getProduct().getName());
				cartDTO.setProductCode(item.getProduct().getCode());
				cartDTO.setPrice(item.getProduct().getPrice());
				cartDTO.setTotal(item.getTotal());
				cartDTO.setQuantity(item.getQuantity());
				cartDTO.setSessionId(item.getSessionId());
				cartDTO.setThumbnail(item.getProduct().getThumbnail());
				cartDTO.setId(item.getId());
				cartDTO.setSize(item.getSize());
				cartDTO.setColor(item.getColor());
				cartDTOS.add(cartDTO);
				total += item.getTotal();
			}
		}
		result.put("products", cartDTOS);
		result.put("total", total);
		return result;
	}

	@Override
	@Transactional
	public CartDTO addToCart(Long productId, CartDTO cartDTO) throws NotPermissionAddToCart {
		try {
			ProductEntity productEntity = productRepository.findById(productId).get();
			if (productEntity.getNumberOfSell() == 0) {
				throw new NotPermissionAddToCart("");
			}

			CartDTO result = new CartDTO();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = null;
			String sessionId = null;
			if (auth != null && !auth.getPrincipal().equals("anonymousUser")) {
				userId = SecurityUtils.getPrincipal().getId();
			} else if (cartDTO.getSessionId() != null) {
				sessionId = cartDTO.getSessionId();
			} else if (cartDTO.getSessionId() == null) {
				sessionId = StringUtils.generateValue(4);
			}
			CartEntity cartEntity = null;
			if (cartRepository.findByProduct_IdAndSizeAndColor(productId, cartDTO.getSize(),
					cartDTO.getColor()) != null) {
				cartEntity = cartRepository.findByProduct_IdAndSizeAndColor(productId, cartDTO.getSize(),
						cartDTO.getColor());
				cartEntity.setQuantity(cartEntity.getQuantity() + cartDTO.getNumber());
				cartEntity.setTotal(cartEntity.getQuantity() * productEntity.getPrice());
			} else {
				cartEntity = new CartEntity();
				cartEntity.setProduct(productEntity);
				cartEntity.setQuantity(cartDTO.getNumber());
				cartEntity.setTotal(cartDTO.getNumber() * productEntity.getPrice());
				cartEntity.setColor(cartDTO.getColor());
				cartEntity.setSize(cartDTO.getSize());
			}
			if (userId != null) {
				cartEntity.setUser(userRepository.findById(userId).get());
			}
			if (sessionId != null) {
				cartEntity.setSessionId(sessionId);
			}
			cartRepository.save(cartEntity);
			result.setSessionId(sessionId);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void editQuantityCart(Long cartId, CartDTO cartDTO) {
		try {
			CartEntity cartEntity = cartRepository.findById(cartId).get();
			cartEntity.setQuantity(cartDTO.getNumber());
			cartEntity.setTotal(cartEntity.getQuantity() * (cartEntity.getProduct().getPrice()));
			cartRepository.save(cartEntity);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteCart(Long cartId) {
		try {
			cartRepository.deleteById(cartId);
		} catch (Exception e) {
			throw e;
		}
	}
}
