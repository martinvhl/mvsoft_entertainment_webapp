package cz.mvsoft.dao.securityDao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
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
		//Possible SQL-Injection vulnerability - use Criteria API - check later!!!
		Session session = entityManager.unwrap(Session.class);
		Query<User> theQuery = session.createQuery("from User where username=:userName",User.class);
		theQuery.setParameter("userName", userName);
		User foundUser = null;
		try {
			foundUser = theQuery.getSingleResult();
		} catch (Exception e) {
			foundUser = null;
		}
		return foundUser;
	}

	@Override
	public void save(User user) { //TODO Query just a workaround, find out why hibernate is not persisting unidirectional @ManyToMany User->Role
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
	}
}
