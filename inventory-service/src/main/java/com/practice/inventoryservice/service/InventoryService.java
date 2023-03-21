package com.practice.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.inventoryservice.dto.InventoryResponse;
import com.practice.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryService {
	
	private final InventoryRepository inventoryRepository;

	public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

}
