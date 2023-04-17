package cz.mvsoft.dao.securityDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cz.mvsoft.entity.users.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	@Qualifier("securityEntityManagerFactory")
	EntityManager entityManager;

	@Override
	public User findByUserName(String userName) {
		TypedQuery<User> query = entityManager.createQuery("from User where userName=:userName", User.class);
		query.setParameter("userName", userName);
		User foundUser = null;
		try {
			foundUser = query.getSingleResult();
		} catch (Exception e) {
			foundUser = null;
		}
		return foundUser;
	}

	@Override
	public User save(User user) {
		return entityManager.merge(user);
	}
}
