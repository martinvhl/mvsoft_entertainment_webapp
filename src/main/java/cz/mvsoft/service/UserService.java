package cz.mvsoft.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import cz.mvsoft.entity.users.User;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);
	
	public User save(User user);
}
