package cz.mvsoft.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.mvsoft.dao.securityDao.RoleDao;
import cz.mvsoft.dao.securityDao.UserDao;
import cz.mvsoft.entity.users.Role;
import cz.mvsoft.entity.users.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private RoleDao roleDao;
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public User save(User user) {
		// TODO User => UserDTO mapping (use MapStruct)
		//todo create userEntertainmentDTO - vytvořit danou entitu pro uložení jen těch polí, která jsou potřeba do entertainment_section db
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_BASIC")));
		return userDao.save(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
