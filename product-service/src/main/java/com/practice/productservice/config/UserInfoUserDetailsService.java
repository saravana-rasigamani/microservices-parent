package com.practice.productservice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.practice.productservice.entity.UserInfo;
import com.practice.productservice.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo =  userRepository.findByName(username);
		return userInfo.map(UserInfoUserDetails:: new)
		.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		
		
	}

}
