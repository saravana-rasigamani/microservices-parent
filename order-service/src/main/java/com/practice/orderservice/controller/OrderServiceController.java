package com.practice.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.orderservice.dto.OrderRequest;
import com.practice.orderservice.entity.Order;
import com.practice.orderservice.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderServiceController {
	
	private OrderService orderService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String placeOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.placeOrder(orderRequest);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Order> getOrders() {
		return orderService.getOrders();
	}

}
