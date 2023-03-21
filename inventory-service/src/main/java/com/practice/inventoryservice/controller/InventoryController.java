package com.practice.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.inventoryservice.dto.InventoryResponse;
import com.practice.inventoryservice.service.InventoryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
	
    private final InventoryService inventoryService;

	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }

}
