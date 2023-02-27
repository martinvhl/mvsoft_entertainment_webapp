package cz.mvsoft.dao;

import cz.mvsoft.entity.users.User;

public interface UserDao {
	
	public User findByUserName(String userName);
    
    public void save(User user);
}
