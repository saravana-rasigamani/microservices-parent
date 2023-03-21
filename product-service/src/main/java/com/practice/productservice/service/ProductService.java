package com.practice.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.productservice.dto.ProductRequest;
import com.practice.productservice.dto.ProductResponse;
import com.practice.productservice.entity.Product;
import com.practice.productservice.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;

	public void saveProduct(ProductRequest productRequest) {
		Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
	
		productRepository.save(product);
	}

	public List<ProductResponse> getAllproducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this:: mapToProductResponse).toList();
	}
	
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

}
