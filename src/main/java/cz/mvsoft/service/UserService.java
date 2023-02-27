package cz.mvsoft.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import cz.mvsoft.entity.users.User;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);
	
	void save(User user);
}
