package com.practice.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.productservice.dto.AuthRequest;
import com.practice.productservice.dto.ProductRequest;
import com.practice.productservice.dto.ProductResponse;
import com.practice.productservice.entity.UserInfo;
import com.practice.productservice.service.JwtService;
import com.practice.productservice.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {
	
	private ProductService productService;
	
	private JwtService jwtService;
	
	private AuthenticationManager authenticationManager;
	
	@GetMapping(value = "/welcome")
	public String welcome() {
		return "Welcome to product service!";
	}
	
	@PostMapping("/saveProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequest productRequest) {
		productService.saveProduct(productRequest);
	}
	
	@GetMapping("/getProducts")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<ProductResponse> getProducts() {
		return productService.getAllproducts();
	}
	
	@GetMapping("/getProducts/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ProductResponse getProductsById(@PathVariable int id) {
		return productService.getProductsById(id);
	}
	
	@PostMapping("/addUser")
	public String addNewUser(@RequestBody UserInfo userInfo){
		return productService.addUser(userInfo);
	}
	
	@PostMapping("/generateToken")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername()) ;
		}
		else {
			throw new UsernameNotFoundException("Invalid user!!");
		}
	}

}
