package com.practice.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
