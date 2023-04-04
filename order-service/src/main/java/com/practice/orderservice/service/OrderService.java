package com.practice.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.practice.orderservice.dto.InventoryResponse;
import com.practice.orderservice.dto.OrderLineItemsDto;
import com.practice.orderservice.dto.OrderRequest;
import com.practice.orderservice.entity.Order;
import com.practice.orderservice.entity.OrderLineItems;
import com.practice.orderservice.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final WebClient.Builder webClientBuilder;
	
	public String placeOrder(OrderRequest orderRequest) {
		Order order =new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
		.stream()
		.map(this::mapToDTO)
		.toList();
		
		order.setOrderLineItemsList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemsList().stream()
		.map(OrderLineItems :: getSkuCode)
		.toList();
		
		InventoryResponse[] inventoryResponse = webClientBuilder.build().get()
		.uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
		.retrieve()
		.bodyToMono(InventoryResponse[].class)
		.block();
		
		boolean allProductsInStock = Arrays.stream(inventoryResponse)
		.allMatch(InventoryResponse :: isInStock);
		
		if(allProductsInStock) {
			orderRepository.save(order);
			return "Order Placed Successfully";
		}
		else {
			throw new IllegalArgumentException("Product is not in stock, please try again later");
		}
	}
	
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
	
	 private OrderLineItems mapToDTO(OrderLineItemsDto orderLineItemsDto) {
	        OrderLineItems orderLineItems = new OrderLineItems();
	        orderLineItems.setPrice(orderLineItemsDto.getPrice());
	        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
	        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
	        return orderLineItems;
	 }

}
