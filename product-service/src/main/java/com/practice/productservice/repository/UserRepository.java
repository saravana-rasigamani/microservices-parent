package com.practice.productservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.productservice.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
	
	Optional<UserInfo> findByName(String name);

}
