package cz.mvsoft.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import cz.mvsoft.entity.users.User;

@Repository
public class UserRepositoryImpl implements UserDao{

	@Autowired
	EntityManager entityManager;

	@Override
	public User findByUserName(String userName) {
		//Possible SQL-Injection vulnerability - use Criteria API - check later!!!
		Session session = entityManager.unwrap(Session.class);
		Query<User> theQuery = session.createQuery("from User where userName=:userName",User.class);
		theQuery.setParameter("userName", userName);
		User foundUser = null;
		try {
			foundUser = theQuery.getSingleResult();
		} catch (UsernameNotFoundException e) {
			foundUser = null;
		}
		return foundUser;
	}

	@Override
	public void save(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
	}
	
}
