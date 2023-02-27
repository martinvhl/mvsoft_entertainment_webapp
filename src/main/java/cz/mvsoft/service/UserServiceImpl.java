package cz.mvsoft.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.mvsoft.config.UserToUserDetails;
import cz.mvsoft.dao.UserInfoRepository;
import cz.mvsoft.entity.users.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userInfoRepository.findByName(username);
		//pokud uživatele nalezneme, namapujeme jeho údaje do UserDetails, se kterými umí Security pracovat
		return user.map(UserToUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("There is no user of this name in our database!"));
	}

	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

}
