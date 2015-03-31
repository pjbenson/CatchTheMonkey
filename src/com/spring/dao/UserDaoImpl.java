package com.spring.dao;

import java.util.List;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> userList() {
		return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

	@Override
	public User getUser(int userId) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Override
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM User WHERE user_id = "+user.getUserId()).executeUpdate();
		
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBalance(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

}
