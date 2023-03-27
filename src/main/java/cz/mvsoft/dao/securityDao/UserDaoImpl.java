package cz.mvsoft.dao.securityDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cz.mvsoft.entity.users.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	@Qualifier("securityEntityManagerFactory")
	EntityManager entityManager;

	@Override
	public User findByUserName(String userName) {
		TypedQuery<User> query = entityManager.createQuery("from User where username=:userName", User.class);
		query.setParameter("userName", userName);
		//Possible SQL-Injection vulnerability - use Criteria API - check later!!!
//		Session session = entityManager.unwrap(Session.class);
//		Query<User> theQuery = session.createQuery("from User where username=:userName",User.class);
//		theQuery.setParameter("userName", userName);
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
//		Session session = entityManager.unwrap(Session.class);
//		session.saveOrUpdate(user);
		return entityManager.merge(user);
	}
}
