package com.practice.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.productservice.dto.ProductRequest;
import com.practice.productservice.dto.ProductResponse;
import com.practice.productservice.entity.Product;
import com.practice.productservice.entity.UserInfo;
import com.practice.productservice.repository.ProductRepository;
import com.practice.productservice.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;

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
	
	public ProductResponse getProductsById(int id) {
		Product product = productRepository.findById(id).get();
		return mapToProductResponse(product);
	}
	
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userRepository.save(userInfo);
		return "user added to system";
	}

}
